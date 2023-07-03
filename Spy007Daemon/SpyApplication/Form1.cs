using System;
using System.Windows.Forms;
using Spy007.Dao;
using Spy007.Dao.Factories;
using Spy007;
using Microsoft.Win32;
using SpyApplication.Controler;

namespace SpyApplication
{
	/// <summary>
	/// Classe contendo o formulario de registro da aplicacao.
	/// </summary>
	public partial class Form1 : Form
	{


		/// <summary>
		/// Cria uma nova instancia da classe <see cref="Form1"/>.
		/// </summary>
		public Form1()
		{
     		InitializeComponent();
        }

		/// <summary>
		/// Invoca o controlador para registrar a aplicacao no sistema.
		/// </summary>
		/// <param name="sender">Fonte do evento.</param>
		/// <param name="e">A instancia <see cref="System.EventArgs"/> contendo os dados do evento.</param>
        private void button1_Click(object sender, EventArgs e)
        {
            try{
                SystemControler.Instalar(txCaminho.Text);
            } catch (Exception erro) {
                MessageBox.Show("Erro no gravação do Registro: " + erro.Message);
            }
            //Exibe mensagem apos a instalacao bem sucedida
            MessageBox.Show("Spy007 Instalado com Sucesso!");

            Hide();
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            Application.Exit();
        }

	}
}