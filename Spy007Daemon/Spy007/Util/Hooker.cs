using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Kennedy.ManagedHooks;
using System.Diagnostics;

namespace Spy007.Util
{
    /// <summary>
    /// Instala os Hooks para teclado e mouse no sistema. Os hooks são códigos
	/// injetados diretamente no sistema operacional que podem monitorar
	/// eventos de mouse e teclado.
    /// </summary>
    static class Hooker
    {
        // Hook do mouse.
		private static MouseHook mouseHook;
        
		// Hook do teclado.
		private static KeyboardHook kbHook;
        
		// Contador de instaracoes com o mouse.
		private static int mouseCount = 0;

		// Contador de instaracoes com o teclado.
		private static int keyCount = 0;

		// Variavel para verificar o estado da tecla.
		private static bool pressed = false;

        /// <summary>
        /// Obtem ou modifica o contador de utilizacao do mouse.
        /// </summary>
        /// <value>Contador do mouse.</value>
        public static int MouseCount
        {
            get { return Hooker.mouseCount; }
            set { Hooker.mouseCount = value; }
        }

        /// <summary>
		/// Obtem ou modifica o contador de utilizacao do teclado.
        /// </summary>
        /// <value>Contador do teclado.</value>
        public static int KeyCount
        {
            get { return Hooker.keyCount; }
            set { Hooker.keyCount = value; }
        }

        /// <summary>
        /// Instala os hooks de teclado e de mouse.
        /// </summary>
        public static void instalarHooks()
        {
            mouseHook = new MouseHook();
            mouseHook.MouseEvent += new MouseHook.MouseEventHandler(OnMouseEvent);

            kbHook = new KeyboardHook();
            kbHook.KeyboardEvent += new KeyboardHook.KeyboardEventHandler(OnKeyboardEvent);

            mouseHook.InstallHook();
            kbHook.InstallHook();
        }

        /// <summary>
        /// Desinstala os hooks.
        /// </summary>
        public static void desinstalarHooks()
        {
            mouseHook.UninstallHook();
            kbHook.UninstallHook();
        }


        /// <summary>
        /// Evento chamado quando [keyboard event]. Verifica que uma tecla foi
		/// pressionada e incrementa o contador do teclado.
        /// </summary>
        /// <param name="kEvent">Evento do teclado.</param>
        /// <param name="key">Tecla precionada.</param>
        static void OnKeyboardEvent(KeyboardEvents kEvent, System.Windows.Forms.Keys key)
        {
			if (kEvent == KeyboardEvents.KeyDown)
			{
				if (pressed == false)
				{
					Trace.WriteLine("Key " + kEvent + " " + key + " " + pressed);
					keyCount++;
					pressed = true;
				}
			}
			else if (kEvent == KeyboardEvents.KeyUp)
			{
				pressed = false;
			}
        }

        /// <summary>
		/// Evento chamado quando [mouse event]. Verifica se um botao do
		/// mouse foi pressionado e incrementa o contador do mouse.
        /// </summary>
        /// <param name="mEvent">Evento do mouse.</param>
        /// <param name="point">Posicao do apontador.</param>
        static void OnMouseEvent(MouseEvents mEvent, System.Drawing.Point point)
        {
            if (mEvent == MouseEvents.LeftButtonDown || mEvent == MouseEvents.RightButtonDown) {
                Trace.WriteLine("Mouse " + mEvent + " " + point);
                mouseCount++;
            }
        }
    }
}
