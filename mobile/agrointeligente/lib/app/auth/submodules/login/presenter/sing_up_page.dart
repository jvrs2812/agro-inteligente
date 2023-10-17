import 'package:agrointeligente/app/auth/submodules/login/domain/entities/request/sing_up_request.dart';
import 'package:agrointeligente/app/auth/submodules/login/presenter/sing_up_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';

import '../../../../shared/components/text_field_container.dart';

class SingUpPage extends StatefulWidget {
  const SingUpPage({super.key});

  @override
  State<SingUpPage> createState() => _SingUpPageState();
}

class _SingUpPageState extends State<SingUpPage> {
  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  final SingUpController controller = Modular.get<SingUpController>();

  final TextEditingController emailController = TextEditingController();
  final TextEditingController senhaController = TextEditingController();
  final TextEditingController nameController = TextEditingController();
  final TextEditingController cpfController = TextEditingController();

  final focusNodeName = FocusNode();
  final focusNodeCpf = FocusNode();
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
                child: ScopedBuilder<SingUpController, SingUpRequest>(
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
                          controller: nameController,
                          enabled: !controller.isLoading,
                          autocorrect: false,
                          icon: Icons.person,
                          onFieldSubmitted: (_) =>
                              focusNodeEmail.requestFocus(),
                          validator: (nome) {
                            if (nome?.isEmpty ?? true) {
                              return 'Campo obrigatório';
                            }
                            return null;
                          },
                          focusNode: focusNodeName,
                          hintText: "Nome",
                          onChanged: controller.setName,
                          onSaved: (name) => controller.setName(name ?? ''),
                        ),
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
                          onFieldSubmitted: (_) => focusNodeCpf.requestFocus(),
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
                          onSaved: (value) =>
                              controller.setPassword(value ?? ''),
                        ),
                        TextFieldContainer(
                          controller: cpfController,
                          enabled: !controller.isLoading,
                          keyboardType: TextInputType.number,
                          autocorrect: false,
                          icon: Icons.edit_document,
                          onFieldSubmitted: (_) => focusNodeCpf.unfocus(),
                          validator: (cpf) {
                            if (cpf?.isEmpty ?? true) {
                              return 'Campo obrigatório';
                            }
                            return null;
                          },
                          focusNode: focusNodeCpf,
                          hintText: "CPF",
                          onChanged: controller.setCpf,
                          onSaved: (cpf) => controller.setCpf(cpf ?? ''),
                        ),
                        ScopedBuilder<SingUpController, SingUpRequest>(
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
                                          FocusScope.of(context)
                                              .requestFocus(FocusNode());
                                          controller.onSingInPress(state);
                                        }
                                      },
                                      style: ElevatedButton.styleFrom(
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 20, horizontal: 40),
                                        primary: Colors.green,
                                        onSurface: Colors.green,
                                      ),
                                      child: const Text(
                                        "Registrar",
                                        style: TextStyle(color: Colors.white),
                                      ),
                                    ),
                                  ),
                                ),
                              ],
                            );
                          },
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
