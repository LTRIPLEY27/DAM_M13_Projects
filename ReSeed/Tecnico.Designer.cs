namespace ReSeed
{
    partial class Tecnico
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Tecnico));
            tabControl1 = new TabControl();
            tabPage1 = new TabPage();
            button1 = new Button();
            listBox1_TAREASTECNICO = new ListBox();
            label1 = new Label();
            button2 = new Button();
            panel1 = new Panel();
            tabPage3 = new TabPage();
            panel2 = new Panel();
            button3 = new Button();
            panel3 = new Panel();
            label8 = new Label();
            textBox6 = new TextBox();
            textBox5 = new TextBox();
            textBox4 = new TextBox();
            label7 = new Label();
            label6 = new Label();
            label5 = new Label();
            Descripción = new DataGridViewTextBoxColumn();
            gMapControl2 = new GMap.NET.WindowsForms.GMapControl();
            tabControl1.SuspendLayout();
            tabPage1.SuspendLayout();
            panel1.SuspendLayout();
            tabPage3.SuspendLayout();
            panel2.SuspendLayout();
            panel3.SuspendLayout();
            SuspendLayout();
            // 
            // tabControl1
            // 
            tabControl1.Appearance = TabAppearance.Buttons;
            tabControl1.Controls.Add(tabPage1);
            tabControl1.Controls.Add(tabPage3);
            tabControl1.Dock = DockStyle.Left;
            tabControl1.ItemSize = new Size(50, 50);
            tabControl1.Location = new Point(0, 0);
            tabControl1.Multiline = true;
            tabControl1.Name = "tabControl1";
            tabControl1.Padding = new Point(20, 3);
            tabControl1.SelectedIndex = 0;
            tabControl1.Size = new Size(1145, 705);
            tabControl1.TabIndex = 0;
            // 
            // tabPage1
            // 
            tabPage1.BackColor = Color.DarkSeaGreen;
            tabPage1.Controls.Add(button1);
            tabPage1.Controls.Add(listBox1_TAREASTECNICO);
            tabPage1.Controls.Add(label1);
            tabPage1.Controls.Add(button2);
            tabPage1.Controls.Add(panel1);
            tabPage1.Font = new Font("Segoe UI", 9F, FontStyle.Bold, GraphicsUnit.Point);
            tabPage1.Location = new Point(4, 54);
            tabPage1.Name = "tabPage1";
            tabPage1.Padding = new Padding(3);
            tabPage1.Size = new Size(1137, 647);
            tabPage1.TabIndex = 0;
            tabPage1.Text = "TAREAS";
            // 
            // button1
            // 
            button1.Location = new Point(546, 222);
            button1.Name = "button1";
            button1.Size = new Size(165, 43);
            button1.TabIndex = 14;
            button1.Text = "CARGAR COORDENADAS";
            button1.UseVisualStyleBackColor = true;
            button1.Click += btn_cargarCoordenadas;
            // 
            // listBox1_TAREASTECNICO
            // 
            listBox1_TAREASTECNICO.FormattingEnabled = true;
            listBox1_TAREASTECNICO.ItemHeight = 15;
            listBox1_TAREASTECNICO.Location = new Point(546, 43);
            listBox1_TAREASTECNICO.Name = "listBox1_TAREASTECNICO";
            listBox1_TAREASTECNICO.Size = new Size(445, 154);
            listBox1_TAREASTECNICO.TabIndex = 13;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(732, 25);
            label1.Name = "label1";
            label1.Size = new Size(102, 15);
            label1.TabIndex = 12;
            label1.Text = "LISTA DE TAREAS";
            // 
            // button2
            // 
            button2.Location = new Point(20, 586);
            button2.Name = "button2";
            button2.Size = new Size(115, 29);
            button2.TabIndex = 10;
            button2.Text = "SALIR";
            button2.UseVisualStyleBackColor = true;
            button2.Click += button2_Click;
            // 
            // panel1
            // 
            panel1.BackgroundImage = (Image)resources.GetObject("panel1.BackgroundImage");
            panel1.Controls.Add(gMapControl2);
            panel1.Location = new Point(20, 32);
            panel1.Name = "panel1";
            panel1.Size = new Size(498, 460);
            panel1.TabIndex = 0;
            // 
            // tabPage3
            // 
            tabPage3.Controls.Add(panel2);
            tabPage3.Location = new Point(4, 54);
            tabPage3.Name = "tabPage3";
            tabPage3.Size = new Size(1137, 647);
            tabPage3.TabIndex = 2;
            tabPage3.Text = "MODIFICACIÓN DATOS USUARIO";
            tabPage3.UseVisualStyleBackColor = true;
            // 
            // panel2
            // 
            panel2.BackColor = SystemColors.ActiveCaption;
            panel2.Controls.Add(button3);
            panel2.Controls.Add(panel3);
            panel2.Controls.Add(textBox6);
            panel2.Controls.Add(textBox5);
            panel2.Controls.Add(textBox4);
            panel2.Controls.Add(label7);
            panel2.Controls.Add(label6);
            panel2.Controls.Add(label5);
            panel2.Location = new Point(60, 56);
            panel2.Name = "panel2";
            panel2.Size = new Size(375, 291);
            panel2.TabIndex = 0;
            // 
            // button3
            // 
            button3.Location = new Point(234, 217);
            button3.Name = "button3";
            button3.Size = new Size(101, 57);
            button3.TabIndex = 7;
            button3.Text = "MODIFICA";
            button3.UseVisualStyleBackColor = true;
            // 
            // panel3
            // 
            panel3.BackColor = Color.SeaShell;
            panel3.Controls.Add(label8);
            panel3.Location = new Point(21, 12);
            panel3.Name = "panel3";
            panel3.Size = new Size(335, 59);
            panel3.TabIndex = 6;
            // 
            // label8
            // 
            label8.AutoSize = true;
            label8.Font = new Font("Segoe UI", 20F, FontStyle.Regular, GraphicsUnit.Point);
            label8.Location = new Point(58, 10);
            label8.Name = "label8";
            label8.Size = new Size(213, 37);
            label8.TabIndex = 0;
            label8.Text = "Nombre Usuario";
            // 
            // textBox6
            // 
            textBox6.Location = new Point(172, 168);
            textBox6.Name = "textBox6";
            textBox6.Size = new Size(163, 23);
            textBox6.TabIndex = 5;
            // 
            // textBox5
            // 
            textBox5.Location = new Point(172, 139);
            textBox5.Name = "textBox5";
            textBox5.Size = new Size(163, 23);
            textBox5.TabIndex = 4;
            // 
            // textBox4
            // 
            textBox4.Location = new Point(172, 108);
            textBox4.Name = "textBox4";
            textBox4.Size = new Size(163, 23);
            textBox4.TabIndex = 3;
            // 
            // label7
            // 
            label7.AutoSize = true;
            label7.Location = new Point(37, 171);
            label7.Name = "label7";
            label7.Size = new Size(132, 15);
            label7.TabIndex = 2;
            label7.Text = "CONFIRMA PASSWORD";
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(37, 142);
            label6.Name = "label6";
            label6.Size = new Size(68, 15);
            label6.TabIndex = 1;
            label6.Text = "PASSWORD";
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(37, 111);
            label5.Name = "label5";
            label5.Size = new Size(64, 15);
            label5.TabIndex = 0;
            label5.Text = "TELÉFONO";
            // 
            // Descripción
            // 
            Descripción.HeaderText = "Descripción";
            Descripción.Name = "Descripción";
            // 
            // gMapControl2
            // 
            gMapControl2.Bearing = 0F;
            gMapControl2.CanDragMap = true;
            gMapControl2.EmptyTileColor = Color.Navy;
            gMapControl2.GrayScaleMode = false;
            gMapControl2.HelperLineOption = GMap.NET.WindowsForms.HelperLineOptions.DontShow;
            gMapControl2.LevelsKeepInMemory = 5;
            gMapControl2.Location = new Point(26, 31);
            gMapControl2.MarkersEnabled = true;
            gMapControl2.MaxZoom = 2;
            gMapControl2.MinZoom = 2;
            gMapControl2.MouseWheelZoomEnabled = true;
            gMapControl2.MouseWheelZoomType = GMap.NET.MouseWheelZoomType.MousePositionAndCenter;
            gMapControl2.Name = "gMapControl2";
            gMapControl2.NegativeMode = false;
            gMapControl2.PolygonsEnabled = true;
            gMapControl2.RetryLoadTile = 0;
            gMapControl2.RoutesEnabled = true;
            gMapControl2.ScaleMode = GMap.NET.WindowsForms.ScaleModes.Integer;
            gMapControl2.SelectedAreaFillColor = Color.FromArgb(33, 65, 105, 225);
            gMapControl2.ShowTileGridLines = false;
            gMapControl2.Size = new Size(441, 388);
            gMapControl2.TabIndex = 0;
            gMapControl2.Zoom = 0D;
            // 
            // Tecnico
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            ClientSize = new Size(1070, 705);
            Controls.Add(tabControl1);
            Name = "Tecnico";
            Text = "TÉCNICO";
            tabControl1.ResumeLayout(false);
            tabPage1.ResumeLayout(false);
            tabPage1.PerformLayout();
            panel1.ResumeLayout(false);
            tabPage3.ResumeLayout(false);
            panel2.ResumeLayout(false);
            panel2.PerformLayout();
            panel3.ResumeLayout(false);
            panel3.PerformLayout();
            ResumeLayout(false);
        }

        #endregion

        private TabControl tabControl1;
        private TabPage tabPage1;
        private TabPage tabPage3;
        private Panel panel1;
        private GMap.NET.WindowsForms.GMapControl gMapControl1;
        private Button button2;
        private Panel panel2;
        private Label label7;
        private Label label6;
        private Label label5;
        private Button button3;
        private Panel panel3;
        private Label label8;
        private TextBox textBox6;
        private TextBox textBox5;
        private TextBox textBox4;
        private Button button4;
        private ComboBox comboBox1;
        private Label label1;
        private DataGridViewTextBoxColumn Descripción;
        private ListBox listBox1_TAREASTECNICO;
        private Button button1;
        private GMap.NET.WindowsForms.GMapControl gMapControl2;
    }
}