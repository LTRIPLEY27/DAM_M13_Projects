using Newtonsoft.Json.Linq;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.Entity.Core.Common.CommandTrees.ExpressionBuilder;
using Xunit.Sdk;

namespace ReSeed
{
    internal class Utilidades
    {
        private Conexion_BD conexion = new Conexion_BD();

        #region MÉTODOS-UTILIDADES JSON
        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon
         */
        public JObject JsonUsuario(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("password", usuario.Password);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;

        }

        /*
         * MÉTODO QUE PARA SERIALIZAR UN objeto Usuario y delver el String
         */
        public String serializarUsuario(Usuario usuario)
        {
            String jsonUsuario = JsonConvert.SerializeObject(usuario);
            return jsonUsuario;

        }

        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon (sin password)
         */
        public JObject JsonUsuario_sinPassword(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;
        }

        /*
         * MÉTODO QUE TRANSFORMA UN OBJETO USUARIO EN Objeto JSon (con password)
         */
        public JObject JsonUsuario_conPassword(Usuario usuario)
        {
            JObject json = new JObject();
            json.Add("nombre", usuario.Nombre);
            json.Add("apellido", usuario.Apellido);
            json.Add("user", usuario.User);
            json.Add("password", usuario.Password);
            json.Add("email", usuario.Mail);
            json.Add("telefono", usuario.NumeroTelefono);
            json.Add("rol", usuario.Rol);

            return json;
        }

        /*
         * MÉTODO QUE PARA SERIALIZAR UN objeto Post y delver el String
         */
        public String serializarUsuario(Post usuario)
        {
            String jsonUsuario = JsonConvert.SerializeObject(usuario);
            return jsonUsuario;

        }

        #endregion


        /*
         * Método ASYNC obtenerRolAsync
         * -----------------------------
         * -Pasamos dos parámetros (String usuario y String token)
         * -Buscamos en el registro de usuarios al usuario pasado por paámetro, si se encuentra, obtenemos su rol
         * y salimos del bucle con la variable de semáforo @usuarioEncontrado. Retornaremos el String
         */
        public async Task <String> obtenerRolAsync (String usuario,String token)
        {
            //Variable booleana para poder salir del LOOP FOR
            Boolean usuarioEncontrado = false;
            //Variable rol que contendrá el rol del usuario pasado por parámetro
            String rol = null;
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(token,"https://t-sunlight-381215.lm.r.appspot.com/results/usuarios");
            for (int i = 0; i < listaUsuarios.Count && !usuarioEncontrado; i++)
            {
                if (usuario.Equals(listaUsuarios[i].Email))
                {

                    rol = listaUsuarios[i].Rol;
                    usuarioEncontrado = true;

                }
            }

            return rol;
        }

        /*
         * Método obtenerPassword
         * -----------------------
         * Recibe 2 parámetros:
         * @usuario
         * @token
         * 
         * Busca en la lista de usuarios al usuario pasado por parámetro y retorna el password.
         * @return password
         */
        public async Task<String> obtenerPassword (String usuario, String token)
        {
            //Variable booleana para poder salir del LOOP FOR
            Boolean usuarioEncontrado = false;
            //Variable rol que contendrá el rol del usuario pasado por parámetro
            String password = null;
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(token, "https://t-sunlight-381215.lm.r.appspot.com/results/usuarios");
            for (int i = 0; i < listaUsuarios.Count && !usuarioEncontrado; i++)
            {
                if (usuario.Equals(listaUsuarios[i].Email))
                {

                    password = listaUsuarios[i].Password;
                    usuarioEncontrado = true;

                }
            }

            return password;
        }


    }
}
