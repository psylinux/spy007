using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Spy007.Dao
{
	/// <summary>
	/// Interface que define as operacoes para os usuarios. Um usuario e 
	/// definido pela secao corrente do sistema.
	/// </summary>
    public interface IUsuario
    {
		/// <summary>
		/// Escreve em algum meio persistente o numero de eventos de mouse e teclado.
		/// </summary>
		/// <param name="teclado">Numero de eventos do teclado.</param>
		/// <param name="mouse">Numero de eventos do mouse.</param>
        void Escrever(int teclado, int mouse);

		/// <summary>
		/// Salva a instancia atual do usuario em algum meio persistente.
		/// </summary>
		void Salvar();
    }
}
