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

namespace ReSeed
{
    public partial class Form3 : Form
    {
        //VARIABLES GLOBALES MAPAS
        private GMarkerGoogle marcador;//Instanciamos marcadores
        private GMapOverlay capaMarcado;//Instanciamos la capa donde se añadirán los marcadores        

        //VARIABLE PARA ALMACENAR TOKEN QUE RECIBO DE LA CLASE Conexion_BD
        private String TOKEN_form3;

        //PASAMOS AL CONTRUCTOR FORM3 EL STRING QUE RECIBIREMOS
        public Form3(String TOKEN_Login)
        {
            TOKEN_form3 = TOKEN_Login;//Indicamos que el String que recibiremos será igual al

            InitializeComponent();

            gMapControl1.DragButton = MouseButtons.Left;
            gMapControl1.CanDragMap = true;
            gMapControl1.MapProvider = GMapProviders.GoogleMap;
            gMapControl1.Position = new PointLatLng(40.463667, -3.74922);//COORDENADAS ESPAÑA

            gMapControl1.MinZoom = 0;//Minimo zoom que el usuario puede realizar
            gMapControl1.MaxZoom = 24;//Maximo zoom que el usuario puede realizar
            gMapControl1.Zoom = 5;//zoom que inicializaremos por defecto (5- dado que queremos que se centre en España)

            gMapControl1.AutoScroll = true;

            gMapControl1.ShowTileGridLines = false;//Quitamos las lineas de coordenadas


        }

        private void button1_Click(object sender, EventArgs e)
        {


        }
        /*
         * Este botón cierra la aplicación
         */
        private void btn_principal_Click(object sender, EventArgs e)
        {

            MessageBox.Show(TOKEN_form3);
            MessageBox.Show("Gracias por utilizar nuestro Programa. Esperamos verte pronto!", "INFORMACIÓN", MessageBoxButtons.OK, MessageBoxIcon.Information);
            Application.Exit();

        }
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
    }
}
