using GMap.NET.MapProviders;
using GMap.NET;
using GMap.NET.WindowsForms.Markers;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using GMap.NET.WindowsForms;
using System.Net;
using System.Diagnostics.Eventing.Reader;
using System.ComponentModel.Design;
using Newtonsoft.Json.Serialization;
using Newtonsoft.Json;
using System.Security.Policy;
using System.Text.Json.Nodes;
using System.Text.Json;

namespace ReSeed
{
    public partial class Administrador : Form
    {
        //URLs-END POINT
        private String URL_registrarUsuarios = "https://t-sunlight-381215.lm.r.appspot.com/register";
        private String URL_usuariosRegistrados = "https://t-sunlight-381215.lm.r.appspot.com/results/usuarios";
        private String URL_elimarUsuario = "https://t-sunlight-381215.lm.r.appspot.com/delete/typus/usuario/id/";//A falta de añador a ID del usuario
        private string URL_modificarUsuario = " https://t-sunlight381215.lm.r.appspot.com/update/value/usuario/id/";

        //VARIABLES GLOBALES MAPAS
        private GMarkerGoogle marcador;//Instanciamos marcadores
        private GMapOverlay capaMarcado;//Instanciamos la capa donde se añadirán los marcadores

        //OBJETO USUARIO
        private Usuario usuario;
        private Conexion_BD conexion = new Conexion_BD();


        //VARIABLE PARA ALMACENAR TOKEN QUE RECIBO DE LA CLASE Conexion_BD
        private String TOKEN_form3;

        //PASAMOS AL CONTRUCTOR FORM3 EL STRING QUE RECIBIREMOS
        public Administrador(String TOKEN_Login)
        {
            TOKEN_form3 = TOKEN_Login;//Indicamos que el String que recibiremos será igual al

            InitializeComponent();
            //CARGAMOS MAPA Y CARACTERISTICAS AL INICIAR EL FORM
            gMapControl1.DragButton = MouseButtons.Left;
            gMapControl1.CanDragMap = true;
            gMapControl1.MapProvider = GMapProviders.GoogleMap;
            gMapControl1.Position = new PointLatLng(40.463667, -3.74922);//COORDENADAS ESPAÑA
            gMapControl1.MinZoom = 0;//Minimo zoom que el usuario puede realizar
            gMapControl1.MaxZoom = 24;//Maximo zoom que el usuario puede realizar
            gMapControl1.Zoom = 5;//zoom que inicializaremos por defecto (5- dado que queremos que se centre en España)
            gMapControl1.AutoScroll = true;
            gMapControl1.ShowTileGridLines = false;//Quitamos las lineas de coordenadas
            //CARGAMOS REGISTRO DE USUARIOS AL ARRANCAR EL FORM
            mostrarRegistro();

        }

        #region BOTÓN CIERRE APLICACIÓN
        /*
         * Este botón cierra la aplicación
         */
        private void btn_principal_Click(object sender, EventArgs e)
        {

            MessageBox.Show("Gracias por utilizar nuestro Programa. Esperamos verte pronto!", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            Application.Exit();

        }
        #endregion

        #region BOTÓN CANCELAR COORDENADAS
        /*
         * El botón cancela restaura todos los valores, es decir, elimina todos los campos
         * que hayamos editado haciendo un RESET
         */
        private void btn_cancelar_Click(object sender, EventArgs e)
        {

            calendario.ResetText();//reseteamos el dia configurado. Se configura al dia actual

            textBox_latitud.ResetText();//borramos latitud
            textBox_longitud.ResetText();//borramos longitud
            textBox_comentarios.ResetText();//borramos comentarios

            comboBox_tareas.ResetText();//borramos tareas
            comboBox_usuarios.ResetText();//borramos usuarios

            registroCoordenadas.Rows.Clear();//borramos todas las coordenadas del datagrid
            capaMarcado.Clear();//eliminamos la capa poligono que dibujamos en el mapa

        }
        #endregion

        #region OBTENCIÓN COORDENADAS (LATITUD Y LONGITUD)
        /*
         * Hacemos un evento que funcionará haciendo doble click en el mapa:
         * -Obtendremos la longitud y la latitud y almacenaremos los datos en los textbox correspondientes.
         */
        private void gMapControl1_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            //Obtenemos los datos de la latitud y la longitud del lugar donde se pulse
            double latitud = gMapControl1.FromLocalToLatLng(e.X, e.Y).Lat;
            double longitud = gMapControl1.FromLocalToLatLng(e.X, e.Y).Lng;

            //Ubicamos las coordenas en el lugar de los textboxs correspondientes
            textBox_latitud.Text = latitud.ToString();
            textBox_longitud.Text = longitud.ToString();

        }
        #endregion

        #region REGISTRAR COORDENADAS EN DATAGRID
        /*
         *Este botón se encarga bde registrar las coordenadas en el dataGrid 
         */
        private void boton_registroCoordenadas_Click(object sender, EventArgs e)
        {
            double latitud = Convert.ToDouble(textBox_latitud.Text);
            double longitud = Convert.ToDouble(textBox_longitud.Text);

            if (latitud != null && longitud != null)//si los datos no son vacios --> agregamos al gridData
            {

                registroCoordenadas.Rows.Add(latitud, longitud);

            }
            else
            {
                MessageBox.Show("No ha introducido coordenadas");
            }

        }
        #endregion

        #region DIBUJAR POLÍGONO
        /*
         * Este botón se encarga de crear una capa encima del mapa y unir los puntos
         * de las diferentes coordenadas almacenadas en el dataGrid.
         * Con los trazos en todos los puntos se creará la figura geométrica que representará
         * la zona que se tiene que trabajar en el mapa (tipo de tarea específica)
         */
        private void btn_dibujarpoligono_Click(object sender, EventArgs e)
        {

            capaMarcado = new GMapOverlay();//objeto de la nueva capa que irá encima del mapa
            List<PointLatLng> puntos = new List<PointLatLng>();//listado que contendrá los puntos marcados en el mapa

            //necesitaremos las variables para almacenar los datos (longitud y latitud)
            double latitud;
            double longitud;

            //Recorremos los datos del grid con un FOR y los añadimos los puntos al LIST
            for (int i = 0; i < registroCoordenadas.Rows.Count - 1; i++)//Restamos a la longitud maxima 1 (dado que por defecto aparece coordenada 0,0)
            {
                latitud = Convert.ToDouble(registroCoordenadas.Rows[i].Cells[0].Value);//añadimos lat (columna inicial pos=0)
                longitud = Convert.ToDouble(registroCoordenadas.Rows[i].Cells[1].Value);//añadimos long (segunda columna pos=1)
                puntos.Add(new PointLatLng(latitud, longitud));//añadimos información al list

            }

            GMapPolygon crearcionPoligono = new GMapPolygon(puntos, "Poligono");//Creamos objeto poligono al cual le pasamos las coordenadas del list
            capaMarcado.Polygons.Add(crearcionPoligono);//añadimos a la capa poligonos el poligono creado con las coordenadas
            gMapControl1.Overlays.Add(capaMarcado);//añadimos la capa al mapa
            //con estas dos lineas de zoom vamos actualizando el mapa
            gMapControl1.Zoom = gMapControl1.Zoom + 1;
            gMapControl1.Zoom = gMapControl1.Zoom - 1;


        }
        #endregion

        #region ELIMINAR FILA SELECCIONADA DATAGRID COORDENADAS
        /*
         * Botón que eliminará la fila seleccionada
         */
        private void btn_eliminarCoordenada_Click(object sender, EventArgs e)
        {

            if (registroCoordenadas.Rows.Count == 1)//si hay solo una fila, NO ELIMINAMOS (por defecto ponemos una fila vacia)
            {
                MessageBox.Show("No hay coordenadas que eliminar.", "MENSAJE ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);//Nostramos mensaje

            }
            else//si por el contrario hay mas de una fila...
            {
                registroCoordenadas.Rows.RemoveAt(registroCoordenadas.CurrentRow.Index);//elimiamos la fila que selecciona el usuario
                capaMarcado.Clear();//borramos capa de dibujo poligono
            }

        }
        #endregion

        #region ELIMINAR TODAS LAS COORDENADAS DEL DATAGRID
        /*
         * Metodo que elimina todas las coordenadas del mapa y desdibuja
         */
        private void btn_eliminarTodasLasCoordenadas_Click(object sender, EventArgs e)
        {
            if (registroCoordenadas.Rows.Count == 1)//si hay solo una fila, NO ELIMINAMOS (por defecto ponemos una fila vacia)
            {
                MessageBox.Show("No hay coordenadas que eliminar.", "MENSAJE ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);//Nostramos mensaje

            }
            else//si por el contrario hay mas de una fila...
            {
                registroCoordenadas.Rows.Clear();//elimiamos todas las filas
                capaMarcado.Clear();//borramos capa de dibujo poligono
            }
        }
        #endregion

        #region ALTA DE USUARIOS
        private void button_ENVIAR_Click(object sender, EventArgs e)
        {
            //Declaramos las variables que compondrán el objeto usuario
            String nombre;
            String apellido;
            String user;
            String password;
            String email;
            String telefono;
            String rol = null;

            //Con este condicional IF controlamos que el usuario solo pueda marcar una opción 
            //y en consecuéncia determinar si será técnico o no
            if (checkBox_TECNIC.Checked)
            {
                rol = "TECNIC";
                checkBox_ADMIN.Enabled = false;//desactivamos checkbox admin

            }
            else if (checkBox_ADMIN.Checked)
            {
                rol = "ADMIN";
                checkBox_TECNIC.Enabled = false;//desactivamos checkbox tecnic
            }

            else
            {
                MessageBox.Show("Solo es posible marcar una opción para rol!", "ERROR ROL", MessageBoxButtons.OK, MessageBoxIcon.Error);
                checkBox_ADMIN.Enabled = true;
                checkBox_TECNIC.Enabled = true;
            }

            //Variable repetición password
            String password_repeticíon = textBox_PASSWORD_CONFIRM.Text;

            //Asignamos los textBox que introducirá el usuario
            nombre = textBox_NOMBRE.Text;
            apellido = textBox_APELLIDO.Text;
            telefono = textBox_TELEFONO.Text;//Casteamos a INT el telefono
            user = textBox_user.Text;
            email = textBox_MAIL.Text;
            password = textBox_PASSWORD.Text;


            //Instanciamos objeto usuario y le pasamos sus parámetros correspondientes
            usuario = new Usuario(nombre, apellido, user, password, email, telefono, rol);

            //Si el password introducido por el usuario coincide con su repetición...
            if (password.Equals(password_repeticíon))
            {
                conexion.altaUsuario(usuario, TOKEN_form3, URL_registrarUsuarios);

                //una vez el usuario está registrado,limpiamos los textbox
                textBox_NOMBRE.Clear();
                textBox_APELLIDO.Clear();
                textBox_TELEFONO.Clear();
                textBox_user.Clear();
                textBox_MAIL.Clear();
                textBox_PASSWORD.Clear();
                textBox_PASSWORD_CONFIRM.Clear();

                checkBox_ADMIN.Checked = false;
                checkBox_TECNIC.Checked = false;

            }
            else
            {//Si las contraseñas no coinciden al confirmar, mostraremos mensaje de erro al usuario
                MessageBox.Show("Las contraseñas introducidas no coinciden. Por favor, revise la contraseña y introdúzcala nuevamente. Gracias", "ERROR CONTRASEÑA", MessageBoxButtons.OK, MessageBoxIcon.Error);
                //Enviamos el focus() a la contraseña puesta que es erronea
                textBox_PASSWORD.Focus();
            }

        }
        #endregion

        #region MOSTRAR REGISTRO DE USUARIOS
        /*
         * Este botón permitairá MOSTRAR USUARIOS registrados en la base de datos
         */
        private async void mostrarRegistro()
        {
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);

            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                String id = listaUsuarios[i].Id;
                String user = listaUsuarios[i].User;
                String email = listaUsuarios[i].Email;

                dataGridView_usuarios.Rows.Add(id, user, email);

            }
        }
        #endregion

        #region ELIMINAR USUARIO
        /*
         * Botón ELIMINAR usuarios
         */
        private async void button2_Click(object sender, EventArgs e)
        {

            //Obtenemos la lista de usuarios
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);
            //Obtenemos la posición en el Datagrid
            int numeroSeleccionado = dataGridView_usuarios.CurrentRow.Index;
            //Creamos variable que almacena POSICION
            int posicionUsuarioEnList = 0;

            //Recorremos el listado de Usuarios
            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                posicionUsuarioEnList = i;//Misma estructuta y posición entre Datagrid y List

                if (posicionUsuarioEnList == numeroSeleccionado)//cuando la posición del Datagrid sea igual a la posición del LIST...
                {
                    String id = listaUsuarios[i].Id;//Obtenemos la Id del Usuario
                    //Mostramos al Usuario la pregunta de si esta seguro de eliminar al usuario n...
                    DialogResult result = MessageBox.Show("¿Está seguro de querer eliminar al usuario con ID: " + id + "de la base de datos?",
                        "ELIMINAR USUARIO", MessageBoxButtons.YesNoCancel, MessageBoxIcon.Warning);
                    if (result == DialogResult.Yes)//si esta seguro en eliminar...Eliminamos de la BD, del Datagrid y mostramos mensaje
                    {
                        conexion.eliminarUsuarioAsync(URL_elimarUsuario, TOKEN_form3, id);
                        dataGridView_usuarios.Rows.RemoveAt(i);
                        MessageBox.Show("Usuario con ID: " + listaUsuarios[i].Id + ", ha sido eliminado de la base de datos correctamente.",
                            "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    else if (result == DialogResult.No)//si no esta seguro en eliminar usuario...Mostramos mensaje
                    {
                        MessageBox.Show("No se ha eliminado ningún registro.",
                           "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }
                    else
                    {
                        MessageBox.Show("Eliminación del usuario cancelada",//si cancela, mostramos mensaje
                           "ELIMINAR USUARIO", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    }

                }
            }
        }
        #endregion

        #region ACTUALIZAR USUARIOS
        /*
         * Botón ACTUALIZAR
         * Limpia por completo el registro datagrid y carga nuevamente todos los usuarios de la base de datos
         */
        private void button3_ACTUALIZARLISTA_Click(object sender, EventArgs e)
        {
            dataGridView_usuarios.Rows.Clear();
            mostrarRegistro();
        }
        #endregion

        #region MODIFICAR USUARIO
        /*
         * Botón para MODIFICAR usuarios
         */
        private async void button1_Click_1(object sender, EventArgs e)
        {
            List<Post> listaUsuarios = await conexion.ObtenerUsuarios(TOKEN_form3, URL_usuariosRegistrados);

            //Obtenemos la posición en el Datagrid
            int numeroSeleccionado = dataGridView_usuarios.CurrentRow.Index;
            //Creamos variable que almacena POSICION
            int posicionUsuarioEnList = 0;
            //Recorremos el listado de Usuarios
            for (int i = 0; i < listaUsuarios.Count; i++)
            {
                posicionUsuarioEnList = i;//Misma estructuta y posición entre Datagrid y List

                if (posicionUsuarioEnList == numeroSeleccionado)//cuando la posición del Datagrid sea igual a la posición del LIST...
                {

                }
            }
        }
            #endregion
        }
    }
