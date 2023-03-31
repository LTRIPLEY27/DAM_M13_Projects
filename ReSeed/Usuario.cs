    using System;
using System.Collections.Generic;
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
        private int numeroTelefono;
        private String mail;
        private String password;

        //Constructor Usuario
        public Usuario(String nombre, String apellido, int numeroTelefono, String mail, String password)
        {

            this.nombre = nombre;
            this.apellido = apellido;
            this.numeroTelefono = numeroTelefono;
            this.mail = mail;
            this.password = password;

        }

        //Getters i Setters
        public String Nombre//Propiedad nombre
        {
            get { return nombre; }
            set { nombre = value; }

        }

        public String Apellido//Propiedad apellido1
        {
            get { return apellido; }
            set { apellido = value; }
        }

        public int NumeroTelefono//Propiedad telefono
        {
            get { return numeroTelefono; }
            set { numeroTelefono = value; }

        }
        
        public String Mail//Propiedad mail
        {
            get { return mail; }
            set { mail = value; }

        }

        public String Password//Propiedad password
        {

            get { return password; } 
            set {  password = value; } 

        }

    }
}
