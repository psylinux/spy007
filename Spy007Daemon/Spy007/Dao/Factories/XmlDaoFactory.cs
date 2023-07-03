using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.IO;
using Microsoft.Win32;
using System.Windows.Forms;

namespace Spy007.Dao.Factories
{
	/// <summary>
	/// Fabrica concreta para usuarios persistidos em XML.
	/// </summary>
    public class XmlDaoFactory : DaoFactory
    {

        public static string spypath = null;
		public static string imgPath = null;

        /// <summary>
        /// Obtem o caminho para o arquivo XML onde serao salvos os dados do
		/// usuario.
        /// </summary>
        /// <value>The arquivo usuario.</value>
        internal static string ArquivoUsuario
        {
            get
            {
                // Abre a chave (Pasta) para leitura do caminho Arquivo.XML
                RegistryKey registro = Registry.LocalMachine.OpenSubKey("SOFTWARE");
                try
                { 
                    // Le o caminho do Arquivo.XML
                    spypath = registro.OpenSubKey("Spy007").GetValue("spypath").ToString();
                }
                catch (Exception erro)
                {
                    MessageBox.Show("Erro na leitura do Registro: " + erro.Message);
                }

                // Obtem o caminho para o arquivo XML
				string user = System.Environment.UserName;
                string path = Path.Combine(spypath, @"usuarios\" + user);
				string data = DateTime.Now.ToString("yyyyMMdd");
				string nomeArq = Path.Combine(path, data + ".xml");
				imgPath = Path.Combine(path, data);

                // Cria o diretorio com o nome do usuario caso nao exista.
				if (!Directory.Exists(path))
                    Directory.CreateDirectory(path);

				if (!Directory.Exists(imgPath))
					Directory.CreateDirectory(imgPath);

                // Cria o arquivo XML com a tag raiz caso nao exista.
				if (!File.Exists(nomeArq)) {
                    XmlDocument xdoc = new XmlDocument();

                    XmlNode xnode = xdoc.CreateXmlDeclaration("1.0", "utf-8", null);

                    XmlNode root = xdoc.CreateElement("Jornada");
                    root.Attributes.Append(xdoc.CreateAttribute("usuario")).Value = user;
                    //root.Attributes.Append(xdoc.CreateAttribute("dia")).Value = DateTime.Now.ToString("dd");
                    //root.Attributes.Append(xdoc.CreateAttribute("mes")).Value = DateTime.Now.ToString("MM");
                    //root.Attributes.Append(xdoc.CreateAttribute("ano")).Value = DateTime.Now.ToString("yyyy");
                    root.Attributes.Append(xdoc.CreateAttribute("inicioExpediente")).Value = DateTime.Now.ToString("HH:mm");

                    xdoc.AppendChild(xnode);
                    xdoc.AppendChild(root);

                    xdoc.Save(nomeArq);
                }

                return nomeArq;
            }
        }

        /// <summary>
        /// Retorna a instancia de usuario para persistencia em XML.
        /// </summary>
        /// <returns>O usuario XML</returns>
        public override IUsuario GetUsuarioDao()
        {
            return new XmlUsuario();
        }
    }
}
