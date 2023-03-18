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
        private String apellido1;
        private String apellido2;
        private int numeroTelefono;
        private String mail;
        private String password;

        //Constructor Usuario
        public Usuario(String nombre, String apellido1, String apellido2, int numeroTelefono, String mail, String password)
        {

            this.nombre = nombre;
            this.apellido1 = apellido1;
            this.apellido2 = apellido2;
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

        public String Apellido1//Propiedad apellido1
        {
            get { return apellido1; }
            set { apellido1 = value; }
        }

        public String Apellido2//Propiedad apellido2
        {
            get { return apellido2; }
            set { apellido2 = value; }

        }

        public int NumeroTelefono//Propiedad telefono
        {
            get { return numeroTelefono; }
            set { numeroTelefono = value; }

        }
        //
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
