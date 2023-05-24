using GMap.NET;
using GMap.NET.MapProviders;
using GMap.NET.WindowsForms;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ReSeed
{
    public partial class Tecnico : Form
    {
        //Variable que usamos para almacenar el token de la conexion del usuario
        private String TOKEN_Login;
        Conexion_BD conexion = new Conexion_BD();
        Utilidades utilidades = new Utilidades();

        //ENDPOINT PERFIL
        private String URLperfil = "https://reseed-385107.ew.r.appspot.com/perfil";

        public Tecnico(String token)//pasamos al constructor del form la variable que enviamos de clase Conexion_BD
        {
            TOKEN_Login = token;//la variable que recibimos ser igual a la que hemos creado @TOKEN_Login

            InitializeComponent();
            //Cargamos las tareas del técnico
            cargarTareas();
            //cargamos mapa Inicial
            cargaMapaInicialTecnico();

        }

        public void cargaMapaInicialTecnico ()
        {
            gMapControl2.DragButton = MouseButtons.Left;
            gMapControl2.CanDragMap = true;
            gMapControl2.MapProvider = GMapProviders.GoogleMap;
            gMapControl2.Position = new PointLatLng(40.463667, -3.74922);//COORDENADAS ESPAÑA
            gMapControl2.MinZoom = 0;//Minimo zoom que el usuario puede realizar
            gMapControl2.MaxZoom = 24;//Maximo zoom que el usuario puede realizar
            gMapControl2.Zoom = 5;//zoom que inicializaremos por defecto (5- dado que queremos que se centre en España)
            gMapControl2.AutoScroll = true;
            gMapControl2.ShowTileGridLines = false;//Quitamos las lineas de coordenadas
        }

        /*
         * El método cargarTareas cargará las tareas del usuario técnico logueado y las mostrará en pantalla menú
         * principal.
         */
        public async void cargarTareas()
        {

            List<Tarea> listaTareasTecnico = await conexion.listaTareasTecnicoASYNC(TOKEN_Login, URLperfil);

            foreach (Tarea tarea in listaTareasTecnico)
            {
                listBox1_TAREASTECNICO.Items.Add(tarea.Id + "-" + tarea.Name + " | " + tarea.Fecha_culminacion);

            }

        }
        private async void btn_cargarCoordenadas(object sender, EventArgs e)
        {
            List<PointLatLng> puntos = new List<PointLatLng>();//listado que contendrá los puntos marcados en el mapa
            GMapOverlay capaMarcado = new GMapOverlay();//objeto para pintar encima del mapa

            //cargamos la lista de tareas
            List<Tarea> listaTareas = await conexion.listaTareasTecnicoASYNC(TOKEN_Login, URLperfil);
            //Obtenemos la info del tecnico clicado
            String infoTecnicoEnLista = listBox1_TAREASTECNICO.SelectedItem.ToString();
            //Separaremos la cadena para obtener su id
            String[] partesInfoTecnico = infoTecnicoEnLista.Split("-");
            //Obtenemos la id que esta en posicion 0 (antes de "-")
            String idTarea = partesInfoTecnico[0];

            //Obtenemos las coordenadas de la tarea seleccionada con el método ObtenerCoordenadasTecnico de
            //la clase utilidades
            Coordenada[] mapa = utilidades.ObtenerCoordenadasTecnico(listaTareas, idTarea);
            //recorremos array coordendas
            for (int i = 0; i < mapa.Length; i++)
            {
                double latitud = mapa[i].Latitud;//obtenemos la latitud de cada iteracion
                double longitud = mapa[i].Longitud;//obtenemos la longitud de cada iteracion

                puntos.Add(new PointLatLng(latitud, longitud));//añadimos información al list de puntos de coordendas

            }

            GMapPolygon crearcionPoligono = new GMapPolygon(puntos, "Poligono");//Creamos objeto poligono al cual le pasamos las coordenadas del list
            capaMarcado.Polygons.Add(crearcionPoligono);//añadimos a la capa poligonos el poligono creado con las coordenadas
            gMapControl2.Overlays.Add(capaMarcado);//añadimos la capa al mapa
            //con estas dos lineas de zoom vamos actualizando el mapa
            gMapControl2.Zoom = gMapControl2.Zoom + 1;
            gMapControl2.Zoom = gMapControl2.Zoom - 1;

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
