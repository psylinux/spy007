using System;
using System.Windows.Forms;
using System.Diagnostics;
using SpyApplication.Controler;


namespace WindowsFormsApplication1
{
	/// <summary>
	/// Classe contendo o codigo de inicializacao da aplicacao.
	/// </summary>
	static class Program
	{
		/// <summary>
		/// Ponto de entrada da aplicação.
		/// </summary>
		[STAThread]
		static void Main()
		{
			Application.EnableVisualStyles();
			Application.SetCompatibleTextRenderingDefault(false);

            // Configura o controlador.
			SystemControler.Configurar();
            
			// Roda a aplicacao.
            Application.Run();

		}
	}
}
