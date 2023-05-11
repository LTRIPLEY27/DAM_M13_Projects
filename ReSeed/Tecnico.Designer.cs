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
            button2 = new Button();
            button1 = new Button();
            textBox3 = new TextBox();
            label4 = new Label();
            label3 = new Label();
            checkedListBox1 = new CheckedListBox();
            textBox2 = new TextBox();
            label1 = new Label();
            label2 = new Label();
            textBox1 = new TextBox();
            panel1 = new Panel();
            gMapControl1 = new GMap.NET.WindowsForms.GMapControl();
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
            tabPage1.Controls.Add(button2);
            tabPage1.Controls.Add(button1);
            tabPage1.Controls.Add(textBox3);
            tabPage1.Controls.Add(label4);
            tabPage1.Controls.Add(label3);
            tabPage1.Controls.Add(checkedListBox1);
            tabPage1.Controls.Add(textBox2);
            tabPage1.Controls.Add(label1);
            tabPage1.Controls.Add(label2);
            tabPage1.Controls.Add(textBox1);
            tabPage1.Controls.Add(panel1);
            tabPage1.Font = new Font("Segoe UI", 9F, FontStyle.Bold, GraphicsUnit.Point);
            tabPage1.Location = new Point(4, 54);
            tabPage1.Name = "tabPage1";
            tabPage1.Padding = new Padding(3);
            tabPage1.Size = new Size(1137, 647);
            tabPage1.TabIndex = 0;
            tabPage1.Text = "TAREAS";
            // 
            // button2
            // 
            button2.Location = new Point(20, 603);
            button2.Name = "button2";
            button2.Size = new Size(95, 23);
            button2.TabIndex = 10;
            button2.Text = "SALIR";
            button2.UseVisualStyleBackColor = true;
            button2.Click += button2_Click;
            // 
            // button1
            // 
            button1.Location = new Point(766, 603);
            button1.Name = "button1";
            button1.Size = new Size(255, 23);
            button1.TabIndex = 9;
            button1.Text = "ENVIAR";
            button1.UseVisualStyleBackColor = true;
            button1.Click += button1_Click;
            // 
            // textBox3
            // 
            textBox3.Location = new Point(766, 467);
            textBox3.Multiline = true;
            textBox3.Name = "textBox3";
            textBox3.Size = new Size(255, 116);
            textBox3.TabIndex = 8;
            // 
            // label4
            // 
            label4.AutoSize = true;
            label4.Location = new Point(766, 449);
            label4.Name = "label4";
            label4.Size = new Size(134, 15);
            label4.TabIndex = 7;
            label4.Text = "DEJA TU COMENTARIO";
            // 
            // label3
            // 
            label3.AutoSize = true;
            label3.Location = new Point(766, 360);
            label3.Name = "label3";
            label3.Size = new Size(187, 15);
            label3.TabIndex = 6;
            label3.Text = "MARCAR TAREA/S REALIZADA/S";
            // 
            // checkedListBox1
            // 
            checkedListBox1.FormattingEnabled = true;
            checkedListBox1.Items.AddRange(new object[] { "REPLANTAR", "DESBROZAR", "LIMPIEZA" });
            checkedListBox1.Location = new Point(766, 378);
            checkedListBox1.Name = "checkedListBox1";
            checkedListBox1.Size = new Size(255, 58);
            checkedListBox1.TabIndex = 5;
            // 
            // textBox2
            // 
            textBox2.Location = new Point(766, 201);
            textBox2.Multiline = true;
            textBox2.Name = "textBox2";
            textBox2.Size = new Size(255, 108);
            textBox2.TabIndex = 4;
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Location = new Point(766, 183);
            label1.Name = "label1";
            label1.Size = new Size(195, 15);
            label1.TabIndex = 3;
            label1.Text = "COMENTARIOS ADMINISTRADOR";
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.BackColor = Color.Transparent;
            label2.Location = new Point(766, 32);
            label2.Name = "label2";
            label2.Size = new Size(122, 15);
            label2.TabIndex = 1;
            label2.Text = "TAREAS A REALIZAR:";
            // 
            // textBox1
            // 
            textBox1.Location = new Point(766, 50);
            textBox1.Multiline = true;
            textBox1.Name = "textBox1";
            textBox1.Size = new Size(255, 108);
            textBox1.TabIndex = 2;
            // 
            // panel1
            // 
            panel1.BackgroundImage = (Image)resources.GetObject("panel1.BackgroundImage");
            panel1.Controls.Add(gMapControl1);
            panel1.Location = new Point(20, 32);
            panel1.Name = "panel1";
            panel1.Size = new Size(679, 551);
            panel1.TabIndex = 0;
            // 
            // gMapControl1
            // 
            gMapControl1.Bearing = 0F;
            gMapControl1.CanDragMap = true;
            gMapControl1.EmptyTileColor = Color.Navy;
            gMapControl1.GrayScaleMode = false;
            gMapControl1.HelperLineOption = GMap.NET.WindowsForms.HelperLineOptions.DontShow;
            gMapControl1.LevelsKeepInMemory = 5;
            gMapControl1.Location = new Point(53, 57);
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
            gMapControl1.ShowTileGridLines = false;
            gMapControl1.Size = new Size(574, 445);
            gMapControl1.TabIndex = 0;
            gMapControl1.Zoom = 0D;
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
        private Label label2;
        private GMap.NET.WindowsForms.GMapControl gMapControl1;
        private TextBox textBox2;
        private Label label1;
        private Button button2;
        private Button button1;
        private TextBox textBox3;
        private Label label4;
        private Label label3;
        private CheckedListBox checkedListBox1;
        private TextBox textBox1;
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
    }
}