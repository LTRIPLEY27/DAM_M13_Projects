    using System;
using System.Collections.Generic;
using System.Drawing.Text;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    
    internal class Usuario
    {
        //Atributos de Usuario
        private String nombre;
        private String apellido;
        private String usuario;
        private String telefono;
        private String email;
        private String password;
        private String rol;

        //Constructor Usuario
        public Usuario(String nombre, String apellido, String usuario, String password, String email, String telefono, String rol)
        {

            this.nombre = nombre;
            this.apellido = apellido;
            this.usuario = usuario;
            this.password = password;
            this.email = email;
            this.telefono = telefono;
            this.rol = rol;
            

        }

        //Getters i Setters
        public String Nombre//Propiedad nombre
        {
            get { return nombre; }
            set { nombre = value; }

        }

        public String User
        {
            get { return usuario; }
            set { usuario = value; }

        }


        public String Apellido//Propiedad apellido1
        {
            get { return apellido; }
            set { apellido = value; }
        }

        public String NumeroTelefono//Propiedad telefono
        {
            get { return telefono; }
            set { telefono = value; }

        }
        
        public String Mail//Propiedad mail
        {
            get { return email; }
            set { email = value; }

        }

        public String Password//Propiedad password
        {

            get { return password; } 
            set {  password = value; } 

        }

        public String Rol
        {
            get { return rol; }
            set { rol = value; }

        }
        

    }
}
