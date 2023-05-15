package com.example.connectfood

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

@Suppress("DEPRECATION")
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //identificando os CHECKBOX
        val doadorCheckBox = findViewById<CheckBox>(R.id.signUpcheckBoxDoador)
        val instituicaoCheckBox = findViewById<CheckBox>(R.id.signUpcheckBoxInstituicao)

        //Identificando os EditText da tela de cadastro
        val inputCNPJ = findViewById<EditText>(R.id.signUpInputCNPJ)
        val inputEmail = findViewById<EditText>(R.id.signUpInputEmail)
        val inputTelefone = findViewById<EditText>(R.id.signUpInputTelephone)
        val inputCEP = findViewById<EditText>(R.id.signUpInputCEP)
        val inputBairro = findViewById<EditText>(R.id.signUpInputBairro)
        val inputEndereco = findViewById<EditText>(R.id.signUpInputEndereco)
        val inputCidade = findViewById<EditText>(R.id.signUpInputCidade)
        val inputEstado = findViewById<EditText>(R.id.signUpInputEstado)
        val inputComplemento = findViewById<EditText>(R.id.signUpInputComplemento)
        val inputPassword = findViewById<EditText>(R.id.signUpInputPassword)
        val inputConfirmPassword = findViewById<EditText>(R.id.signUpInputConfirmPassword)
        val textViewPasswordRules = findViewById<TextView>(R.id.signUpPasswordRules)

        //Pegando o botão de cadastre-se e botão/texto de sign In
        val btnSignUp = findViewById<MaterialButton>(R.id.btnSignUp)
        val btnTxtSignIn = findViewById<TextView>(R.id.txtSignIn)

        //Função para fazer o REQUEST e gerar o autoComplet
        fun requestCNPJ (formatedCNPJ: String){
            val url = "https://www.receitaws.com.br/v1/cnpj/$formatedCNPJ"
            val request = Request.Builder().url(url).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    val empresaCadastro = Gson().fromJson(json, EmpresaCadastro::class.java)
                    val error = empresaCadastro.message

                    if (error.equals("CNPJ inválido")) {
                        runOnUiThread{
                            Toast.makeText(this@SignUpActivity, "CNPJ não encontrado", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val rua = empresaCadastro.logradouro
                        val numeroEmpresa = empresaCadastro.numero
                        val enderecoCompleto = "$rua, $numeroEmpresa"

                        runOnUiThread {
                            inputEmail.setText(empresaCadastro.email)
                            inputTelefone.setText(empresaCadastro.telefone)
                            inputCEP.setText(empresaCadastro.cep)
                            inputBairro.setText(empresaCadastro.bairro)
                            inputCidade.setText(empresaCadastro.municipio)
                            inputEstado.setText(empresaCadastro.uf)
                            inputComplemento.setText(empresaCadastro.complemento)
                            inputEndereco.setText(enderecoCompleto)
                        }
                    }
                }
            })
        }

        // função de focus off para quando o usuario terminar de digitar o CPF
        inputCNPJ.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                //Pegando os valores de todos os EditTex
                val stringCNPJ = inputCNPJ.text.toString()
                if (stringCNPJ.isNotEmpty()) {
                    //Formando o CNPJ para fazer o request : 01.123.456/0001-10 -> 01123456000110
                    val formatedCNPJ = stringCNPJ.replace("\\D".toRegex(), "")
                    requestCNPJ (formatedCNPJ)
                } else {
                    Toast.makeText(this, "Preencha o CNPJ corretamente!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Função para ir para tela de login
        btnTxtSignIn.setOnClickListener{
            val signInIntent = Intent(this, LoginActivity::class.java)
            startActivity(signInIntent)
        }

        //Função para desabilitar o checkbox da instituição se o usuario for doador
        doadorCheckBox.setOnCheckedChangeListener { _, isChecked ->
            instituicaoCheckBox.isEnabled = !isChecked
        }

        //Função para desabilitar o checkbox do doador se o usuario for instituição
        instituicaoCheckBox.setOnCheckedChangeListener { _, isChecked ->
            doadorCheckBox.isEnabled = !isChecked
        }

        //Função para confirmar o cadastro do usuario
        btnSignUp.setOnClickListener{

            //Pegando os textos de todos os inputs
            val stringCNPJ = inputCNPJ.text.toString().replace("\\D".toRegex(), "")
            val stringEmail = inputEmail.text.toString()
            val stringTelefone = inputTelefone.text.toString().replace("\\D".toRegex(), "")
            val stringCEP = inputCEP.text.toString()
            val stringBairro = inputBairro.text.toString()
            val stringEndereco = inputEndereco.text.toString()
            val stringCidade = inputCidade.text.toString()
            val stringEstado = inputEstado.text.toString()
            val stringComplemento = inputComplemento.text.toString()
            val stringPassword = inputPassword.text.toString()
            val stringConfirmPassword = inputConfirmPassword.text.toString()

            val inputStringList = listOf(
                stringCNPJ,
                stringEmail,
                stringTelefone,
                stringCEP,
                stringBairro,
                stringEndereco,
                stringCidade,
                stringEstado,
                stringComplemento,
                stringPassword,
                stringConfirmPassword
            )

            //Função para requisitos minimos de senha
            fun validarSenha(stringPassword: String): Boolean {
                val regex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$")
                return regex.matches(stringPassword)
            }

            //Função para confirmação das senhas
            fun validarSenhasIguais(stringPassword: String, stringConfirmPassword: String): Boolean {
                return stringPassword == stringConfirmPassword
            }

            //Condicionais para validação do cadastro
            if (!doadorCheckBox.isChecked && !instituicaoCheckBox.isChecked) {
                Toast.makeText(this, "Favor informar se você é doador ou instituição", Toast.LENGTH_SHORT).show()
            } else if (inputStringList.any { it.isBlank() }) {
                Toast.makeText(this, "Favor preencher todos os campos!", Toast.LENGTH_SHORT).show()
            } else if (!validarSenha(stringPassword)) {
                Toast.makeText(this, "Senha inválida!", Toast.LENGTH_SHORT).show()
                textViewPasswordRules.visibility = View.VISIBLE
            } else if (!validarSenhasIguais(stringPassword, stringConfirmPassword)) {
                Toast.makeText(this, "As senhas não são iguais!", Toast.LENGTH_SHORT).show()
                textViewPasswordRules.visibility = View.GONE
            } else {
                Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("login", stringCNPJ)
                intent.putExtra("senha", stringPassword)
                startActivity(intent)
            }
        }

    }
}