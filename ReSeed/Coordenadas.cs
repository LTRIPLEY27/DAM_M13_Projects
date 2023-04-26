using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ReSeed
{
    internal class Coordenadas
    {
        private long latitud;
        private long longitud;
        public Coordenadas(long latitud, long longitud) {
        
            this.latitud = latitud;
            this.longitud = longitud;   

        }

        public long Latitud { get; set;}
        public long Longitud { get; set;}

    }
}
