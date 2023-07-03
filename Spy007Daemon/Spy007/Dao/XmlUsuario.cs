using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using Spy007.Dao.Factories;
using System.Drawing;
using System.Windows.Forms;
using System.Drawing.Imaging;
using System.IO;
using System.Drawing.Drawing2D;

namespace Spy007.Dao
{
	/// <summary>
	/// Classe de persistencia do usuario em arquivo XML. Modo: montagem e 
	/// sobrescrita do arquivo.
	/// </summary>
    public class XmlUsuario : IUsuario
    {
        XmlDocument xdoc = new XmlDocument();
        XmlNode root;
		
		private Bitmap bmpScreenshot;
		private Graphics gfxScreenshot;
		private int time = 0;


        /// <summary>
        /// Inicializa uma instancia da classe <see cref="XmlUsuario"/>.
        /// </summary>
        public XmlUsuario()
        {
        }

        #region UsuarioDao Members

        /// <summary>
        /// Escreve no arquivo xml os dados coletados do ultimo minuto.
        /// </summary>
        /// <param name="teclado">The teclado.</param>
        /// <param name="mouse">The mouse.</param>
        public void Escrever(int teclado, int mouse)
        {
			Carregar();
			
			XmlElement minuto = xdoc.CreateElement("Minuto");
            minuto.Attributes.Append(xdoc.CreateAttribute("Teclado")).Value = "" + teclado;
            minuto.Attributes.Append(xdoc.CreateAttribute("Mouse")).Value = "" + mouse;
			minuto.Attributes.Append(xdoc.CreateAttribute("dia")).Value = DateTime.Now.ToString("dd");
			minuto.Attributes.Append(xdoc.CreateAttribute("mes")).Value = DateTime.Now.ToString("MM");
			minuto.Attributes.Append(xdoc.CreateAttribute("ano")).Value = DateTime.Now.ToString("yyyy");
			minuto.Attributes.Append(xdoc.CreateAttribute("hora")).Value = DateTime.Now.ToString("HH");
            minuto.Attributes.Append(xdoc.CreateAttribute("minuto")).Value = DateTime.Now.ToString("mm");

            root.AppendChild(minuto);

            Salvar();

			time++;

			if (time == 10)
			{
				GerarScreenshot();
			}

        }

		/// <summary>
		/// Gera um screenshot da tela do usuario.
		/// </summary>
		private void GerarScreenshot()
		{
			// Cria um bitmap do tamanho da tela
			bmpScreenshot = new Bitmap(Screen.PrimaryScreen.Bounds.Width,
				Screen.PrimaryScreen.Bounds.Height, PixelFormat.Format32bppArgb);
			
			// Gera o screenshot
			gfxScreenshot = Graphics.FromImage(bmpScreenshot);
			gfxScreenshot.CopyFromScreen(Screen.PrimaryScreen.Bounds.X,
				Screen.PrimaryScreen.Bounds.Y, 0, 0, Screen.PrimaryScreen.Bounds.Size,
				CopyPixelOperation.SourceCopy);

			// Cria um bitmap com 1/3 do tamanho da tela
			int w = Screen.PrimaryScreen.Bounds.Width / 3;
			int h = Screen.PrimaryScreen.Bounds.Height / 3;
			Bitmap result = new Bitmap(w, h);

			// Transforma o screenshot original em uma imagem com 1/3 do tamanho
			Graphics g = Graphics.FromImage((Image)result);
			g.CompositingQuality = CompositingQuality.HighQuality;
			g.SmoothingMode = SmoothingMode.HighQuality;
			g.InterpolationMode = InterpolationMode.HighQualityBicubic;
			g.DrawImage(bmpScreenshot, 0, 0, w, h);

			// Salva o screenshot
			string path = Path.Combine(XmlDaoFactory.imgPath, DateTime.Now.ToString("HHmm") + ".jpg");
			result.Save(path, ImageFormat.Jpeg);
			time = 0;

		}

		/// <summary>
		/// Carrega em memória o conteúdo do arquivo xml, recuperando a tag Jornada como root.
		/// </summary>
		private void Carregar()
		{
			xdoc.Load(XmlDaoFactory.ArquivoUsuario);
			root = xdoc.GetElementsByTagName("Jornada")[0];
		}

        /// <summary>
        /// Salva o arquivo xml guardando a hora corrente como o fim do expediente.
        /// </summary>
        public void Salvar()
        {
            root.Attributes.Append(xdoc.CreateAttribute("fimExpediente")).Value = DateTime.Now.ToString("HH:mm");

            xdoc.Save(XmlDaoFactory.ArquivoUsuario);
        }

        #endregion
    }
}
