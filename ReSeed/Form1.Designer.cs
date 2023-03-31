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
            label2_PASSWORD = new Label();
            label1_USUARIO = new Label();
            pictureBox4_LOGIN_REESED = new PictureBox();
            pictureBox2_FONDO_LOGIN = new PictureBox();
            panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)pictureBox4_LOGIN_REESED).BeginInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox2_FONDO_LOGIN).BeginInit();
            SuspendLayout();
            // 
            // panel1
            // 
            panel1.BackColor = Color.White;
            panel1.Controls.Add(btn_salir);
            panel1.Controls.Add(btn_conectar);
            panel1.Controls.Add(textBox_password);
            panel1.Controls.Add(textBox_usuario);
            panel1.Controls.Add(label2_PASSWORD);
            panel1.Controls.Add(label1_USUARIO);
            panel1.Controls.Add(pictureBox4_LOGIN_REESED);
            panel1.Controls.Add(pictureBox2_FONDO_LOGIN);
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
            // label2_PASSWORD
            // 
            label2_PASSWORD.AutoSize = true;
            label2_PASSWORD.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            label2_PASSWORD.Location = new Point(138, 116);
            label2_PASSWORD.Name = "label2_PASSWORD";
            label2_PASSWORD.Size = new Size(90, 17);
            label2_PASSWORD.TabIndex = 4;
            label2_PASSWORD.Text = "PASSWORD";
            // 
            // label1_USUARIO
            // 
            label1_USUARIO.AutoSize = true;
            label1_USUARIO.Font = new Font("Tahoma", 10F, FontStyle.Bold, GraphicsUnit.Point);
            label1_USUARIO.Location = new Point(138, 82);
            label1_USUARIO.Name = "label1_USUARIO";
            label1_USUARIO.Size = new Size(75, 17);
            label1_USUARIO.TabIndex = 3;
            label1_USUARIO.Text = "USUARIO";
            // 
            // pictureBox4_LOGIN_REESED
            // 
            pictureBox4_LOGIN_REESED.BackColor = Color.Transparent;
            pictureBox4_LOGIN_REESED.BackgroundImageLayout = ImageLayout.Stretch;
            pictureBox4_LOGIN_REESED.BorderStyle = BorderStyle.Fixed3D;
            pictureBox4_LOGIN_REESED.Image = (Image)resources.GetObject("pictureBox4_LOGIN_REESED.Image");
            pictureBox4_LOGIN_REESED.Location = new Point(61, 12);
            pictureBox4_LOGIN_REESED.Name = "pictureBox4_LOGIN_REESED";
            pictureBox4_LOGIN_REESED.Size = new Size(347, 177);
            pictureBox4_LOGIN_REESED.TabIndex = 2;
            pictureBox4_LOGIN_REESED.TabStop = false;
            // 
            // pictureBox2_FONDO_LOGIN
            // 
            pictureBox2_FONDO_LOGIN.BackColor = Color.White;
            pictureBox2_FONDO_LOGIN.Image = (Image)resources.GetObject("pictureBox2_FONDO_LOGIN.Image");
            pictureBox2_FONDO_LOGIN.Location = new Point(-16, 3);
            pictureBox2_FONDO_LOGIN.Name = "pictureBox2_FONDO_LOGIN";
            pictureBox2_FONDO_LOGIN.Size = new Size(496, 551);
            pictureBox2_FONDO_LOGIN.SizeMode = PictureBoxSizeMode.StretchImage;
            pictureBox2_FONDO_LOGIN.TabIndex = 0;
            pictureBox2_FONDO_LOGIN.TabStop = false;
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
            ((System.ComponentModel.ISupportInitialize)pictureBox4_LOGIN_REESED).EndInit();
            ((System.ComponentModel.ISupportInitialize)pictureBox2_FONDO_LOGIN).EndInit();
            ResumeLayout(false);
        }

        #endregion

        private Panel panel1;
        private PictureBox pictureBox2_FONDO_LOGIN;
        private PictureBox pictureBox4_LOGIN_REESED;
        private Label label2_PASSWORD;
        private Label label1_USUARIO;
        private Button btn_salir;
        private Panel panel3;
        private Button btn_registrar;
        public Button btn_conectar;
        public TextBox textBox_password;
        public TextBox textBox_usuario;
    }
}