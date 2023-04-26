using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ReSeed
{
    public partial class Tecnico : Form
    {
        //Variable que usamos para almacenar el token de la conexion del usuario
        private String TOKEN_Login;
        public Tecnico(String token)//pasamos al constructor del form la variable que enviamos de clase Conexion_BD
        {
            TOKEN_Login = token;//la variable que recibimos ser igual a la que hemos creado @TOKEN_Login
            InitializeComponent();
            
        }

        /*
         * Botón salir:
         * Sale del programa y la sesión finaliza
         */
        private void button2_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        
    }
}
