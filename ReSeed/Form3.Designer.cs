namespace ReSeed
{
    partial class Form3
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form3));
            panel1 = new Panel();
            boton_registroCoordenadas = new Button();
            gMapControl1 = new GMap.NET.WindowsForms.GMapControl();
            panel2 = new Panel();
            label5 = new Label();
            textBox_longitud = new TextBox();
            textBox_latitud = new TextBox();
            label2 = new Label();
            label1 = new Label();
            ConfigMapa = new TabControl();
            tabPage1 = new TabPage();
            btn_dibujarpoligono = new Button();
            btn_eliminarTodasLasCoordenadas = new Button();
            btn_eliminarCoordenada = new Button();
            calendario = new DateTimePicker();
            registroCoordenadas = new DataGridView();
            Column1 = new DataGridViewTextBoxColumn();
            Column2 = new DataGridViewTextBoxColumn();
            btn_salir = new Button();
            btn_enviar = new Button();
            btn_cancelar = new Button();
            panel4 = new Panel();
            textBox_comentarios = new TextBox();
            label7 = new Label();
            panel3 = new Panel();
            comboBox_usuarios = new ComboBox();
            label6 = new Label();
            comboBox_tareas = new ComboBox();
            label4 = new Label();
            tabPage2 = new TabPage();
            tabPage3 = new TabPage();
            tabPage4 = new TabPage();
            panel1.SuspendLayout();
            panel2.SuspendLayout();
            ConfigMapa.SuspendLayout();
            tabPage1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)registroCoordenadas).BeginInit();
            panel4.SuspendLayout();
            panel3.SuspendLayout();
            SuspendLayout();
            // 
            // panel1
            // 
            panel1.BackColor = SystemColors.ActiveCaption;
            panel1.Controls.Add(boton_registroCoordenadas);
            panel1.Controls.Add(gMapControl1);
            panel1.Controls.Add(panel2);
            panel1.Location = new Point(34, 25);
            panel1.Name = "panel1";
            panel1.Size = new Size(584, 622);
            panel1.TabIndex = 0;
            // 
            // boton_registroCoordenadas
            // 
            boton_registroCoordenadas.Font = new Font("Tahoma", 12F, FontStyle.Bold, GraphicsUnit.Point);
            boton_registroCoordenadas.Image = (Image)resources.GetObject("boton_registroCoordenadas.Image");
            boton_registroCoordenadas.ImageAlign = ContentAlignment.TopCenter;
            boton_registroCoordenadas.Location = new Point(405, 467);
            boton_registroCoordenadas.Name = "boton_registroCoordenadas";
            boton_registroCoordenadas.Size = new Size(163, 135);
            boton_registroCoordenadas.TabIndex = 2;
            boton_registroCoordenadas.Text = "AÑADIR COORDENADA";
            boton_registroCoordenadas.UseVisualStyleBackColor = true;
            boton_registroCoordenadas.Click += boton_registroCoordenadas_Click;
            // 
            // gMapControl1
            // 
            gMapControl1.Bearing = 0F;
            gMapControl1.CanDragMap = true;
            gMapControl1.EmptyTileColor = Color.Navy;
            gMapControl1.GrayScaleMode = false;
            gMapControl1.HelperLineOption = GMap.NET.WindowsForms.HelperLineOptions.DontShow;
            gMapControl1.LevelsKeepInMemory = 5;
            gMapControl1.Location = new Point(20, 18);
            gMapControl1.MarkersEnabled = true;
            gMapControl1.MaxZoom = 2;
            gMapControl1.MinZoom = 2;
            gMapControl1.MouseWheelZoomEnabled = true;
            gMapControl1.MouseWheelZoomType = GMap.NET.MouseWheelZoomType.MousePositionAndCenter;
            gMapControl1.Name = "gMapControl1";
            gMapControl1.NegativeMode = false;
            gMapControl1.PolygonsEnabled = true;
            gMapControl1.RetryLoadTile = 0;
            gMapControl1.RoutesEnabled = true;
            gMapControl1.ScaleMode = GMap.NET.WindowsForms.ScaleModes.Integer;
            gMapControl1.SelectedAreaFillColor = Color.FromArgb(33, 65, 105, 225);
            gMapControl1.ShowTileGridLines = true;
            gMapControl1.Size = new Size(548, 430);
            gMapControl1.TabIndex = 0;
            gMapControl1.Zoom = 2D;
            gMapControl1.MouseDoubleClick += gMapControl1_MouseDoubleClick;
            // 
            // panel2
            // 
            panel2.BackColor = Color.SkyBlue;
            panel2.Controls.Add(label5);
            panel2.Controls.Add(textBox_longitud);
            panel2.Controls.Add(textBox_latitud);
            panel2.Controls.Add(label2);
            panel2.Controls.Add(label1);
            panel2.Location = new Point(20, 467);
            panel2.Name = "panel2";
            panel2.Size = new Size(349, 135);
            panel2.TabIndex = 1;
            // 
            // label5
            // 
            label5.AutoSize = true;
            label5.Location = new Point(16, 21);
            label5.Name = "label5";
            label5.Size = new Size(103, 16);
            label5.TabIndex = 6;
            label5.Text = "COORDENADAS";
            // 
            // textBox_longitud
            // 
            textBox_longitud.Location = new Point(98, 87);
            textBox_longitud.Name = "textBox_longitud";
            textBox_longitud.ReadOnly = true;
            textBox_longitud.Size = new Size(230, 23);
            textBox_longitud.TabIndex = 4;
            // 
            // textBox_latitud
            // 
            textBox_latitud.Location = new Point(98, 54);
            textBox_latitud.Name = "textBox_latitud";
            textBox_latitud.ReadOnly = true;
            textBox_latitud.Size = new Size(230, 23);
            textBox_latitud.TabIndex = 3;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Location = new Point(16, 90);
            label2.Name = "label2";
            label2.Size = new Size(63, 16);
            label2.TabIndex = 1;
            label2.Text = "Longitud";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(16, 57);
            label1.Name = "label1";
            label1.Size = new Size(53, 16);
            label1.TabIndex = 0;
            label1.Text = "Latitud";
            // 
            // ConfigMapa
            // 
            ConfigMapa.Controls.Add(tabPage1);
            ConfigMapa.Controls.Add(tabPage2);
            ConfigMapa.Controls.Add(tabPage3);
            ConfigMapa.Controls.Add(tabPage4);
            ConfigMapa.Font = new Font("Tahoma", 9.75F, FontStyle.Bold, GraphicsUnit.Point);
            ConfigMapa.Location = new Point(-2, 0);
            ConfigMapa.Name = "ConfigMapa";
            ConfigMapa.SelectedIndex = 0;
            ConfigMapa.Size = new Size(1174, 794);
            ConfigMapa.TabIndex = 1;
            // 
            // tabPage1
            // 
            tabPage1.BackgroundImage = (Image)resources.GetObject("tabPage1.BackgroundImage");
            tabPage1.BackgroundImageLayout = ImageLayout.Stretch;
            tabPage1.Controls.Add(btn_dibujarpoligono);
            tabPage1.Controls.Add(btn_eliminarTodasLasCoordenadas);
            tabPage1.Controls.Add(btn_eliminarCoordenada);
            tabPage1.Controls.Add(calendario);
            tabPage1.Controls.Add(registroCoordenadas);
            tabPage1.Controls.Add(btn_salir);
            tabPage1.Controls.Add(btn_enviar);
            tabPage1.Controls.Add(btn_cancelar);
            tabPage1.Controls.Add(panel4);
            tabPage1.Controls.Add(panel3);
            tabPage1.Controls.Add(panel1);
            tabPage1.Location = new Point(4, 25);
            tabPage1.Name = "tabPage1";
            tabPage1.Padding = new Padding(3);
            tabPage1.Size = new Size(1166, 765);
            tabPage1.TabIndex = 0;
            tabPage1.Text = "Asignación de Tareas";
            tabPage1.UseVisualStyleBackColor = true;
            // 
            // btn_dibujarpoligono
            // 
            btn_dibujarpoligono.BackColor = Color.OliveDrab;
            btn_dibujarpoligono.ImageAlign = ContentAlignment.MiddleLeft;
            btn_dibujarpoligono.Location = new Point(961, 104);
            btn_dibujarpoligono.Name = "btn_dibujarpoligono";
            btn_dibujarpoligono.Size = new Size(124, 70);
            btn_dibujarpoligono.TabIndex = 11;
            btn_dibujarpoligono.Text = "DIBUJAR POLÍGONO";
            btn_dibujarpoligono.TextImageRelation = TextImageRelation.ImageBeforeText;
            btn_dibujarpoligono.UseVisualStyleBackColor = false;
            btn_dibujarpoligono.Click += btn_dibujarpoligono_Click;
            // 
            // btn_eliminarTodasLasCoordenadas
            // 
            btn_eliminarTodasLasCoordenadas.BackColor = Color.OliveDrab;
            btn_eliminarTodasLasCoordenadas.Location = new Point(961, 277);
            btn_eliminarTodasLasCoordenadas.Name = "btn_eliminarTodasLasCoordenadas";
            btn_eliminarTodasLasCoordenadas.Size = new Size(124, 65);
            btn_eliminarTodasLasCoordenadas.TabIndex = 10;
            btn_eliminarTodasLasCoordenadas.Text = "ELIMINAR TODAS LAS COORDENADAS";
            btn_eliminarTodasLasCoordenadas.UseVisualStyleBackColor = false;
            btn_eliminarTodasLasCoordenadas.Click += btn_eliminarTodasLasCoordenadas_Click;
            // 
            // btn_eliminarCoordenada
            // 
            btn_eliminarCoordenada.BackColor = Color.OliveDrab;
            btn_eliminarCoordenada.Location = new Point(962, 193);
            btn_eliminarCoordenada.Name = "btn_eliminarCoordenada";
            btn_eliminarCoordenada.Size = new Size(124, 68);
            btn_eliminarCoordenada.TabIndex = 9;
            btn_eliminarCoordenada.Text = "ELIMINAR COORDENADA SELECCIONADA";
            btn_eliminarCoordenada.UseVisualStyleBackColor = false;
            btn_eliminarCoordenada.Click += btn_eliminarCoordenada_Click;
            // 
            // calendario
            // 
            calendario.ImeMode = ImeMode.NoControl;
            calendario.Location = new Point(711, 59);
            calendario.Name = "calendario";
            calendario.Size = new Size(374, 23);
            calendario.TabIndex = 8;
            calendario.Value = new DateTime(2023, 3, 14, 15, 37, 16, 0);
            // 
            // registroCoordenadas
            // 
            registroCoordenadas.BackgroundColor = SystemColors.ActiveCaption;
            registroCoordenadas.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            registroCoordenadas.Columns.AddRange(new DataGridViewColumn[] { Column1, Column2 });
            registroCoordenadas.Location = new Point(712, 102);
            registroCoordenadas.Name = "registroCoordenadas";
            registroCoordenadas.RowTemplate.Height = 25;
            registroCoordenadas.Size = new Size(243, 240);
            registroCoordenadas.TabIndex = 7;
            // 
            // Column1
            // 
            Column1.HeaderText = "Latitud";
            Column1.MaxInputLength = 8;
            Column1.Name = "Column1";
            // 
            // Column2
            // 
            Column2.HeaderText = "Longitud";
            Column2.MaxInputLength = 8;
            Column2.Name = "Column2";
            // 
            // btn_salir
            // 
            btn_salir.AutoEllipsis = true;
            btn_salir.BackColor = Color.OliveDrab;
            btn_salir.Location = new Point(34, 673);
            btn_salir.Name = "btn_salir";
            btn_salir.Size = new Size(114, 61);
            btn_salir.TabIndex = 6;
            btn_salir.Text = "SALIR";
            btn_salir.UseVisualStyleBackColor = false;
            btn_salir.Click += btn_principal_Click;
            // 
            // btn_enviar
            // 
            btn_enviar.BackColor = Color.OliveDrab;
            btn_enviar.Location = new Point(972, 673);
            btn_enviar.Name = "btn_enviar";
            btn_enviar.Size = new Size(114, 61);
            btn_enviar.TabIndex = 5;
            btn_enviar.Text = "ENVIAR";
            btn_enviar.TextImageRelation = TextImageRelation.TextAboveImage;
            btn_enviar.UseVisualStyleBackColor = false;
            // 
            // btn_cancelar
            // 
            btn_cancelar.BackColor = Color.OliveDrab;
            btn_cancelar.Location = new Point(713, 673);
            btn_cancelar.Name = "btn_cancelar";
            btn_cancelar.Size = new Size(114, 61);
            btn_cancelar.TabIndex = 4;
            btn_cancelar.Text = "CANCELAR";
            btn_cancelar.UseVisualStyleBackColor = false;
            btn_cancelar.Click += btn_cancelar_Click;
            // 
            // panel4
            // 
            panel4.BackColor = SystemColors.ActiveCaption;
            panel4.Controls.Add(textBox_comentarios);
            panel4.Controls.Add(label7);
            panel4.Location = new Point(713, 448);
            panel4.Name = "panel4";
            panel4.Size = new Size(373, 199);
            panel4.TabIndex = 3;
            // 
            // textBox_comentarios
            // 
            textBox_comentarios.Location = new Point(17, 30);
            textBox_comentarios.Multiline = true;
            textBox_comentarios.Name = "textBox_comentarios";
            textBox_comentarios.Size = new Size(340, 149);
            textBox_comentarios.TabIndex = 1;
            // 
            // label7
            // 
            label7.AutoSize = true;
            label7.Location = new Point(15, 11);
            label7.Name = "label7";
            label7.Size = new Size(98, 16);
            label7.TabIndex = 0;
            label7.Text = "COMENTARIOS";
            // 
            // panel3
            // 
            panel3.BackColor = SystemColors.ActiveCaption;
            panel3.Controls.Add(comboBox_usuarios);
            panel3.Controls.Add(label6);
            panel3.Controls.Add(comboBox_tareas);
            panel3.Controls.Add(label4);
            panel3.Location = new Point(712, 348);
            panel3.Name = "panel3";
            panel3.Size = new Size(374, 94);
            panel3.TabIndex = 2;
            // 
            // comboBox_usuarios
            // 
            comboBox_usuarios.FormattingEnabled = true;
            comboBox_usuarios.Location = new Point(207, 41);
            comboBox_usuarios.Name = "comboBox_usuarios";
            comboBox_usuarios.Size = new Size(151, 24);
            comboBox_usuarios.TabIndex = 3;
            // 
            // label6
            // 
            label6.AutoSize = true;
            label6.Location = new Point(207, 13);
            label6.Name = "label6";
            label6.Size = new Size(64, 16);
            label6.TabIndex = 2;
            label6.Text = "USUARIO";
            // 
            // comboBox_tareas
            // 
            comboBox_tareas.FormattingEnabled = true;
            comboBox_tareas.Location = new Point(22, 41);
            comboBox_tareas.Name = "comboBox_tareas";
            comboBox_tareas.Size = new Size(154, 24);
            comboBox_tareas.TabIndex = 1;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(16, 13);
            label4.Name = "label4";
            label4.Size = new Size(50, 16);
            label4.TabIndex = 0;
            label4.Text = "TAREA";
            // 
            // tabPage2
            // 
            tabPage2.Location = new Point(4, 25);
            tabPage2.Name = "tabPage2";
            tabPage2.Padding = new Padding(3);
            tabPage2.Size = new Size(1166, 765);
            tabPage2.TabIndex = 1;
            tabPage2.Text = "Gestión Usuarios";
            tabPage2.UseVisualStyleBackColor = true;
            // 
            // tabPage3
            // 
            tabPage3.Location = new Point(4, 25);
            tabPage3.Name = "tabPage3";
            tabPage3.Padding = new Padding(3);
            tabPage3.Size = new Size(1166, 765);
            tabPage3.TabIndex = 2;
            tabPage3.Text = "Gestión de Tareas";
            tabPage3.UseVisualStyleBackColor = true;
            // 
            // tabPage4
            // 
            tabPage4.Location = new Point(4, 25);
            tabPage4.Name = "tabPage4";
            tabPage4.Padding = new Padding(3);
            tabPage4.Size = new Size(1166, 765);
            tabPage4.TabIndex = 3;
            tabPage4.Text = "Comentarios";
            tabPage4.UseVisualStyleBackColor = true;
            // 
            // Form3
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            BackgroundImageLayout = ImageLayout.Stretch;
            ClientSize = new Size(1115, 790);
            Controls.Add(ConfigMapa);
            Name = "Form3";
            Text = "ADMINISTRADOR";
            panel1.ResumeLayout(false);
            panel2.ResumeLayout(false);
            panel2.PerformLayout();
            ConfigMapa.ResumeLayout(false);
            tabPage1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)registroCoordenadas).EndInit();
            panel4.ResumeLayout(false);
            panel4.PerformLayout();
            panel3.ResumeLayout(false);
            panel3.PerformLayout();
            ResumeLayout(false);
        }

        #endregion

        private Panel panel1;
        private GMap.NET.WindowsForms.GMapControl gMapControl1;
        private Button btn_cancelar;
        private TabControl ConfigMapa;
        private TabPage tabPage1;
        private Panel panel2;
        private TextBox textBox2;
        private TextBox textBox1;
        private Label label2;
        private Label label1;
        private TabPage tabPage2;
        private TabPage tabPage3;
        private TabPage tabPage4;
        private Button btn_enviar;
        private Panel panel4;
        private TextBox textBox4;
        private Label label7;
        private Panel panel3;
        private ComboBox comboBox2;
        private Label label6;
        private ComboBox comboBox1;
        private Label label4;
        private Label label5;
        private TextBox textBox3;
        private Button btn_salir;
        private TextBox textBox_comentarios;
        private ComboBox comboBox_usuarios;
        private ComboBox comboBox_tareas;
        private TextBox textBox_longitud;
        private TextBox textBox_latitud;
        private DataGridView registroCoordenadas;
        private Button boton_registroCoordenadas;
        private Button btn_eliminarTodasLasCoordenadas;
        private Button btn_eliminarCoordenada;
        private DateTimePicker calendario;
        private Button btn_dibujarpoligono;
        private DataGridViewTextBoxColumn Column1;
        private DataGridViewTextBoxColumn Column2;
    }
}