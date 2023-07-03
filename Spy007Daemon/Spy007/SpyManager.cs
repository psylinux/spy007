using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Kennedy.ManagedHooks;
using Spy007.Util;
using System.Timers;
using Spy007.Dao.Factories;
using Spy007.Dao;
using System.Xml;
using System.IO;

namespace Spy007
{
	/// <summary>
	/// Gerenciador do Spy 007.
	/// </summary>
    public class SpyManager
    {
        private static readonly object locker = new object();
        private static SpyManager instance;
        private Timer timer;
        private IUsuario usuario;

        /// <summary>
		/// Inicializa uma nova instancia da classe <see cref="SpyManager"/>. 
		/// Obtendo uma fábrica <see cref="DaoFactory"/> e instanciando um
		/// usuario <see cref="IUsuario"/>.
        /// </summary>
        private SpyManager()
        {
            DaoFactory usuarioFactory = DaoFactory.GetDaoFactory();
            usuario = usuarioFactory.GetUsuarioDao();
        }

        /// <summary>
        /// Inicia o Spy 007. Instala os hooks no sistema e inicia um 
		/// temporizador para registrar os dados coletados do usuario
		/// de minuto a minuto.
        /// </summary>
        public bool Start()
        {
            try {
                Hooker.instalarHooks();

                if (timer != null)
                    return false;

                timer = new Timer(1000 * 60);
                timer.Elapsed += new ElapsedEventHandler(OnTimerElapsed);

                timer.Start();

            } catch (Exception) {
                return false;
            }
            return true;
        }

        /// <summary>
        /// Para o Spy 007. Desinstala os hooks, para o temporizador e
		/// salva os dados do usuario.
        /// </summary>
        public void Stop()
        {
            Hooker.desinstalarHooks();

            timer.Stop();
            timer = null;
            usuario.Salvar();
        }

        /// <summary>
        /// Chamado quando [timer elapsed]. Escreve os dados coletados e
		/// zera os contadores do Hooker.
        /// </summary>
        /// <param name="sender">O objeto que enviou.</param>
        /// <param name="e">A instancia <see cref="System.Timers.ElapsedEventArgs"/> contendo os dados do evento.</param>
        private void OnTimerElapsed(object sender, ElapsedEventArgs e)
        {
            usuario.Escrever(Hooker.KeyCount, Hooker.MouseCount);
            Hooker.KeyCount = Hooker.MouseCount = 0; 
        }

        /// <summary>
        /// Obtem a instancia única do gerenciador.
        /// </summary>
        /// <value>Instancia.</value>
        public static SpyManager Instance
        {
            get {
                lock (locker) {
                    if (instance == null)
                        instance = new SpyManager();
                }

                return instance;
            }
        }
    }
}
