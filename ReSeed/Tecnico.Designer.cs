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
            button6 = new Button();
            button5 = new Button();
            textBox2 = new TextBox();
            textBox1 = new TextBox();
            comboBox2 = new ComboBox();
            label4 = new Label();
            label3 = new Label();
            label2 = new Label();
            button1 = new Button();
            listBox1_TAREASTECNICO = new ListBox();
            label1 = new Label();
            button2 = new Button();
            panel1 = new Panel();
            tabPage3 = new TabPage();
            panel2 = new Panel();
            textBox_REPITE_PASSWORD = new TextBox();
            textBox_PASSWORD = new TextBox();
            label9 = new Label();
            label8 = new Label();
            button3 = new Button();
            panel3 = new Panel();
            label8_usuario = new Label();
            textBox4_TelefonoPerfil = new TextBox();
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
            tabPage1.Controls.Add(button6);
            tabPage1.Controls.Add(button5);
            tabPage1.Controls.Add(textBox2);
            tabPage1.Controls.Add(textBox1);
            tabPage1.Controls.Add(comboBox2);
            tabPage1.Controls.Add(label4);
            tabPage1.Controls.Add(label3);
            tabPage1.Controls.Add(label2);
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
            // button6
            // 
            button6.Location = new Point(777, 229);
            button6.Name = "button6";
            button6.Size = new Size(75, 23);
            button6.TabIndex = 22;
            button6.Text = "VALIDAR";
            button6.UseVisualStyleBackColor = true;
            // 
            // button5
            // 
            button5.Location = new Point(884, 444);
            button5.Name = "button5";
            button5.Size = new Size(101, 48);
            button5.TabIndex = 21;
            button5.Text = "RESPONDER MENSAJE";
            button5.UseVisualStyleBackColor = true;
            // 
            // textBox2
            // 
            textBox2.Location = new Point(546, 367);
            textBox2.Multiline = true;
            textBox2.Name = "textBox2";
            textBox2.Size = new Size(439, 57);
            textBox2.TabIndex = 20;
            // 
            // textBox1
            // 
            textBox1.Location = new Point(546, 290);
            textBox1.Multiline = true;
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(439, 45);
            textBox1.TabIndex = 19;
            // 
            // comboBox2
            // 
            comboBox2.FormattingEnabled = true;
            comboBox2.Location = new Point(608, 229);
            comboBox2.Name = "comboBox2";
            comboBox2.Size = new Size(149, 23);
            comboBox2.TabIndex = 18;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(546, 349);
            label4.Name = "label4";
            label4.Size = new Size(163, 15);
            label4.TabIndex = 17;
            label4.Text = "MENSAJE ADMINISTRADOR";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(546, 272);
            label3.Name = "label3";
            label3.Size = new Size(130, 15);
            label3.TabIndex = 16;
            label3.Text = "INFORMACION TAREA";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(552, 232);
            label2.Name = "label2";
            label2.Size = new Size(50, 15);
            label2.TabIndex = 15;
            label2.Text = "STATUS";
            // 
            // button1
            // 
            button1.Location = new Point(353, 513);
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
            panel2.Controls.Add(textBox_REPITE_PASSWORD);
            panel2.Controls.Add(textBox_PASSWORD);
            panel2.Controls.Add(label9);
            panel2.Controls.Add(label8);
            panel2.Controls.Add(button3);
            panel2.Controls.Add(panel3);
            panel2.Controls.Add(textBox4_TelefonoPerfil);
            panel2.Controls.Add(label5);
            panel2.Location = new Point(60, 56);
            panel2.Name = "panel2";
            panel2.Size = new Size(375, 291);
            panel2.TabIndex = 0;
            // 
            // textBox_REPITE_PASSWORD
            // 
            textBox_REPITE_PASSWORD.Location = new Point(172, 187);
            textBox_REPITE_PASSWORD.Name = "textBox_REPITE_PASSWORD";
            textBox_REPITE_PASSWORD.PasswordChar = '*';
            textBox_REPITE_PASSWORD.Size = new Size(163, 23);
            textBox_REPITE_PASSWORD.TabIndex = 11;
            // 
            // textBox_PASSWORD
            // 
            textBox_PASSWORD.Location = new Point(172, 157);
            textBox_PASSWORD.Name = "textBox_PASSWORD";
            textBox_PASSWORD.PasswordChar = '*';
            textBox_PASSWORD.Size = new Size(163, 23);
            textBox_PASSWORD.TabIndex = 10;
            // 
            // label9
            // 
            label9.AutoSize = true;
            label9.Location = new Point(59, 190);
            label9.Name = "label9";
            label9.Size = new Size(106, 15);
            label9.TabIndex = 9;
            label9.Text = "REPITE PASSWORD";
            // 
            // label8
            // 
            label8.AutoSize = true;
            label8.Location = new Point(59, 160);
            label8.Name = "label8";
            label8.Size = new Size(68, 15);
            label8.TabIndex = 8;
            label8.Text = "PASSWORD";
            // 
            // button3
            // 
            button3.Location = new Point(172, 227);
            button3.Name = "button3";
            button3.Size = new Size(163, 42);
            button3.TabIndex = 7;
            button3.Text = "MODIFICA";
            button3.UseVisualStyleBackColor = true;
            button3.Click += modificaDatosTecnico;
            // 
            // panel3
            // 
            panel3.BackColor = Color.SeaShell;
            panel3.Controls.Add(label8_usuario);
            panel3.Location = new Point(24, 49);
            panel3.Name = "panel3";
            panel3.Size = new Size(335, 59);
            panel3.TabIndex = 6;
            // 
            // label8_usuario
            // 
            label8_usuario.AutoSize = true;
            label8_usuario.Font = new Font("Segoe UI", 20F, FontStyle.Regular, GraphicsUnit.Point);
            label8_usuario.Location = new Point(55, 10);
            label8_usuario.Name = "label8_usuario";
            label8_usuario.Size = new Size(0, 37);
            label8_usuario.TabIndex = 0;
            // 
            // textBox4_TelefonoPerfil
            // 
            textBox4_TelefonoPerfil.Location = new Point(172, 128);
            textBox4_TelefonoPerfil.Name = "textBox4_TelefonoPerfil";
            textBox4_TelefonoPerfil.Size = new Size(163, 23);
            textBox4_TelefonoPerfil.TabIndex = 3;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(59, 131);
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
            gMapControl2.Location = new Point(32, 40);
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
            gMapControl2.Size = new Size(424, 383);
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
        private Label label8_usuario;
        private TextBox textBox6_RepeatPasswordPerfil;
        private TextBox textBox5_PasswordPerfil;
        private TextBox textBox4_TelefonoPerfil;
        private Button button4;
        private ComboBox comboBox1;
        private Label label1;
        private DataGridViewTextBoxColumn Descripción;
        private ListBox listBox1_TAREASTECNICO;
        private Button button1;
        private Button button6;
        private Button button5;
        private TextBox textBox2;
        private TextBox textBox1;
        private ComboBox comboBox2;
        private Label label4;
        private Label label3;
        private Label label2;
        private TextBox textBox_REPITE_PASSWORD;
        private TextBox textBox_PASSWORD;
        private Label label9;
        private Label label8;
        private GMap.NET.WindowsForms.GMapControl gMapControl2;
    }
}