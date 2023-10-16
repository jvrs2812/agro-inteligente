import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';

import '../../../../shared/components/text_field_container.dart';
import '../domain/entities/request/login_credentials.dart';
import 'login_controller.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  final LoginController controller = Modular.get<LoginController>();

  final TextEditingController emailController = TextEditingController();
  final TextEditingController senhaController = TextEditingController();

  final focusNodeEmail = FocusNode();
  final focusNodePassword = FocusNode();

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;
    return Scaffold(
      backgroundColor: Colors.white,
      body: SizedBox(
        height: double.infinity,
        width: size.height,
        child: Stack(
          alignment: Alignment.center,
          children: [
            SingleChildScrollView(
              child: Form(
                key: formKey,
                child: ScopedBuilder<LoginController, LoginCredentials>(
                  store: controller,
                  onState: (context, state) {
                    return Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Image.asset(
                          "assets/logo.png",
                          height: size.height * 0.22,
                        ),
                        SizedBox(height: size.height * 0.03),
                        TextFieldContainer(
                          controller: emailController,
                          enabled: !controller.isLoading,
                          keyboardType: TextInputType.emailAddress,
                          autocorrect: false,
                          icon: Icons.email,
                          onFieldSubmitted: (_) =>
                              focusNodePassword.requestFocus(),
                          validator: (email) {
                            if (email?.isEmpty ?? true) {
                              return 'Campo obrigatório';
                            } else if (!email!.contains('@')) {
                              return 'E-mail inválido';
                            }
                            return null;
                          },
                          focusNode: focusNodeEmail,
                          hintText: "E-mail",
                          onChanged: controller.setEmail,
                          onSaved: (email) => controller.setEmail(email ?? ''),
                        ),
                        TextFieldContainer(
                          controller: senhaController,
                          enabled: !controller.isLoading,
                          isPassword: true,
                          icon: Icons.vpn_key,
                          focusNode: focusNodePassword,
                          onFieldSubmitted: (_) => focusNodePassword.unfocus(),
                          validator: (senha) {
                            if (senha?.isEmpty ?? true) {
                              return 'Campo obrigatório';
                            } else if ((senha ?? '').length < 6) {
                              return 'A senha deve ter no mínimo 6 caractéres';
                            }
                            return null;
                          },
                          hintText: "Senha",
                          onChanged: controller.setPassword,
                        ),
                        ScopedBuilder<LoginController, LoginCredentials>(
                          store: controller,
                          onLoading: (context) {
                            return const CircularProgressIndicator(
                              valueColor:
                                  AlwaysStoppedAnimation<Color>(Colors.green),
                            );
                          },
                          onState: (context, state) {
                            return Column(
                              children: [
                                Container(
                                  margin:
                                      const EdgeInsets.symmetric(vertical: 10),
                                  width: size.width * 0.8,
                                  child: ClipRRect(
                                    borderRadius: BorderRadius.circular(29),
                                    child: ElevatedButton(
                                      onPressed: () {
                                        focusNodeEmail.unfocus();
                                        focusNodePassword.unfocus();
                                        if (formKey.currentState!.validate()) {
                                          controller.onSingInPress(
                                              emailController.text,
                                              senhaController.text);
                                        }
                                      },
                                      style: ElevatedButton.styleFrom(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 20, horizontal: 40),
                                        primary: Colors.green,
                                        onSurface: Colors.green,
                                      ),
                                      child: const Text(
                                        "Entrar",
                                        style: TextStyle(color: Colors.white),
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            );
                          },
                        ),
                        SizedBox(height: size.height * 0.03),
                        InkWell(
                          onTap: () {
                            Modular.to.pushNamed('/login/singup');
                          },
                          child: const Text(
                            'Inscreva-se',
                            style: TextStyle(fontSize: 14, color: Colors.green),
                          ),
                        ),
                      ],
                    );
                  },
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
