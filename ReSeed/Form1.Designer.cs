namespace ReSeed
{
    partial class Form1
    {
        /// <summary>
        ///  Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        ///  Clean up any resources being used.
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
        ///  Required method for Designer support - do not modify
        ///  the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            panel1 = new Panel();
            btn_salir = new Button();
            btn_conectar = new Button();
            textBox_password = new TextBox();
            textBox_usuario = new TextBox();
            label2 = new Label();
            label1 = new Label();
            pictureBox4 = new PictureBox();
            pictureBox2 = new PictureBox();
            panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)pictureBox4).BeginInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox2).BeginInit();
            SuspendLayout();
            // 
            // panel1
            // 
            panel1.BackColor = Color.White;
            panel1.Controls.Add(btn_salir);
            panel1.Controls.Add(btn_conectar);
            panel1.Controls.Add(textBox_password);
            panel1.Controls.Add(textBox_usuario);
            panel1.Controls.Add(label2);
            panel1.Controls.Add(label1);
            panel1.Controls.Add(pictureBox4);
            panel1.Controls.Add(pictureBox2);
            panel1.Location = new Point(0, 0);
            panel1.Name = "panel1";
            panel1.Size = new Size(466, 522);
            panel1.TabIndex = 0;
            // 
            // btn_salir
            // 
            btn_salir.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            btn_salir.Location = new Point(12, 471);
            btn_salir.Name = "btn_salir";
            btn_salir.Size = new Size(80, 37);
            btn_salir.TabIndex = 8;
            btn_salir.Text = "SALIR";
            btn_salir.UseVisualStyleBackColor = true;
            btn_salir.Click += btn_salir_Click;
            // 
            // btn_conectar
            // 
            btn_conectar.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            btn_conectar.Location = new Point(229, 154);
            btn_conectar.Name = "btn_conectar";
            btn_conectar.Size = new Size(148, 27);
            btn_conectar.TabIndex = 7;
            btn_conectar.Text = "INICIAR SESIÓN";
            btn_conectar.UseVisualStyleBackColor = true;
            btn_conectar.Click += btn_conectar_Click;
            // 
            // textBox_password
            // 
            textBox_password.Location = new Point(229, 114);
            textBox_password.Name = "textBox_password";
            textBox_password.PasswordChar = '*';
            textBox_password.Size = new Size(148, 23);
            textBox_password.TabIndex = 6;
            // 
            // textBox_usuario
            // 
            textBox_usuario.Location = new Point(229, 80);
            textBox_usuario.Name = "textBox_usuario";
            textBox_usuario.Size = new Size(148, 23);
            textBox_usuario.TabIndex = 5;
            // 
            // label2
            // 
            label2.AutoSize = true;
            label2.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            label2.Location = new Point(138, 116);
            label2.Name = "label2";
            label2.Size = new Size(90, 17);
            label2.TabIndex = 4;
            label2.Text = "PASSWORD";
            // 
            // label1
            // 
            label1.AutoSize = true;
            label1.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            label1.Location = new Point(138, 82);
            label1.Name = "label1";
            label1.Size = new Size(75, 17);
            label1.TabIndex = 3;
            label1.Text = "USUARIO";
            // 
            // pictureBox4
            // 
            pictureBox4.BackColor = Color.Transparent;
            pictureBox4.BackgroundImageLayout = ImageLayout.Stretch;
            pictureBox4.BorderStyle = BorderStyle.Fixed3D;
            pictureBox4.Image = (Image)resources.GetObject("pictureBox4.Image");
            pictureBox4.Location = new Point(61, 12);
            pictureBox4.Name = "pictureBox4";
            pictureBox4.Size = new Size(347, 177);
            pictureBox4.TabIndex = 2;
            pictureBox4.TabStop = false;
            // 
            // pictureBox2
            // 
            pictureBox2.BackColor = Color.White;
            pictureBox2.Image = (Image)resources.GetObject("pictureBox2.Image");
            pictureBox2.Location = new Point(-11, 0);
            pictureBox2.Name = "pictureBox2";
            pictureBox2.Size = new Size(496, 551);
            pictureBox2.SizeMode = PictureBoxSizeMode.StretchImage;
            pictureBox2.TabIndex = 0;
            pictureBox2.TabStop = false;
            // 
            // Form1
            // 
            AutoScaleDimensions = new SizeF(7F, 15F);
            AutoScaleMode = AutoScaleMode.Font;
            AutoSizeMode = AutoSizeMode.GrowAndShrink;
            ClientSize = new Size(467, 520);
            Controls.Add(panel1);
            FormBorderStyle = FormBorderStyle.None;
            MaximizeBox = false;
            MinimizeBox = false;
            Name = "Form1";
            StartPosition = FormStartPosition.CenterScreen;
            Text = "LOGIN";
            Load += Form1_Load;
            panel1.ResumeLayout(false);
            panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)pictureBox4).EndInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox2).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private Panel panel1;
        private PictureBox pictureBox2;
        private PictureBox pictureBox4;
        private TextBox textBox_password;
        private TextBox textBox_usuario;
        private Label label2;
        private Label label1;
        private Button btn_salir;
        private Panel panel3;
        private Button btn_registrar;
        private Label label4;
        private Label label3;
        public Button btn_conectar;
    }
}