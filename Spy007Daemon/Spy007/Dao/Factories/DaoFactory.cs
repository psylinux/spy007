using System;
using System.Configuration;


namespace Spy007.Dao.Factories
{
	/// <summary>
	/// Fabrica abstrata de usuarios. 
	/// </summary>
    public abstract class DaoFactory
    {
        /// <summary>
        /// Retorna o usuario criado pela fabrica.
        /// </summary>
        /// <returns></returns>
        public abstract IUsuario GetUsuarioDao();

        /// <summary>
        /// Obtem uma instancia de uma fabrica concreta. O tipo da fabrica 
		/// concreta é obtido de um arquivo de configuracao
        /// </summary>
        /// <returns>A instancia da fabrica concreta</returns>
        public static DaoFactory GetDaoFactory()
        {
            string strDaoFactoryType = ConfigurationManager.AppSettings["DaoFactory"];

            if (String.IsNullOrEmpty(strDaoFactoryType)) {
                throw new NullReferenceException("Tipo de fabrica nao existente.");
            } else {
                Type DaoFactoryType = Type.GetType(strDaoFactoryType);

                if (DaoFactoryType == null)
                    throw new NullReferenceException("Tipo da fabrica nao encontrado.");

                DaoFactory daoFactory = (DaoFactory)Activator.CreateInstance(DaoFactoryType);

                return daoFactory;
            }
        }
    }
}
