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
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void label2_Click(object sender, EventArgs e)
        {

        }

        /*
         * Cuando se pulse el botón cancelar, se cerrará la pantalla de
         * registro y se mostrará la pantalla unicial
         */
        private void button2_Click(object sender, EventArgs e)
        {
            Form1 login = new Form1();

            login.Show();

            this.Hide();

        }
    }
}
