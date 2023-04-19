using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Post
    {
        //Atributos de Usuario
        private String id;
        private String user;
        private String password;
        private String nombre;
        private String apellido;
        private String email;
        private String telefono;
        private String rol;

        //Constructor Usuario
        public Post(String id, String user, String password,String nombre, String apellido,String email, String telefono, String rol) 
        {
            this.id = id;
            this.nombre = nombre;
            this.apellido = apellido;
            this.user = user;
            this.password = password;
            this.email = email;
            this.telefono = telefono;
            this.rol = rol;

        }

        //Getters i Setters

        public String Id//Propiedad id
        {
            get { return id; }
            set { id = value; }

        }
        public String Nombre//Propiedad nombre
        {
            get { return nombre; }
            set { nombre = value; }

        }

        public String User//Propiedad user
        {
            get { return user; }
            set { user = value; }

        }


        public String Apellido//Propiedad apellido
        {
            get { return apellido; }
            set { apellido = value; }
        }

        public String NumeroTelefono//Propiedad telefono
        {
            get { return telefono; }
            set { telefono = value; }

        }

        public String Email//Propiedad mail
        {
            get { return email; }
            set { email = value; }

        }

        public String Password//Propiedad password
        {

            get { return password; }
            set { password = value; }

        }

        public String Rol//Propiedad rol
        {
            get { return rol; }
            set { rol = value; }

        }

     
    }
}
