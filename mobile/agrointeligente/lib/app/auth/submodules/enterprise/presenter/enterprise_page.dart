import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/domain/entities/response/EnterpriseResponse.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_controler.dart';
import 'package:agrointeligente/app/auth/submodules/enterprise/presenter/enterprise_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';

class EnterprisePage extends StatefulWidget {
  const EnterprisePage({super.key});

  @override
  State<EnterprisePage> createState() => _EnterprisePageState();
}

class _EnterprisePageState extends State<EnterprisePage> {
  EnterpriseController enterpriseController = Modular.get();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          shadowColor: Colors.green,
          backgroundColor: Colors.green,
          leading: Container(),
          actions: [
            // Botão de logout
            IconButton(
              icon: const Icon(Icons.exit_to_app),
              onPressed: () {
                Modular.get<AuthStore>().logout();
              },
            ),
          ],
        ),
        body: ScopedBuilder<EnterpriseController, List<EnterpriseResponse>>(
          store: enterpriseController,
          onLoading: (context) {
            return const Center(
              child: CircularProgressIndicator(
                valueColor: AlwaysStoppedAnimation<Color>(Colors.green),
                color: Colors.green,
              ),
            );
          },
          onError: (context, error) {
            return Center(
              child: Text(error.message),
            );
          },
          onState: (context, state) {
            return SizedBox(
                height: double.infinity,
                width: double.infinity,
                child: Column(
                  children: [
                    Column(
                      children: [
                        SizedBox(
                          height: MediaQuery.of(context).size.height - 200,
                          width: double.infinity,
                          child: state.isNotEmpty
                              ? GridView.builder(
                                  gridDelegate:
                                      const SliverGridDelegateWithFixedCrossAxisCount(
                                    crossAxisCount:
                                        2, // Número de colunas no grid
                                    crossAxisSpacing:
                                        8.0, // Espaçamento entre as colunas
                                    mainAxisSpacing:
                                        8.0, // Espaçamento entre as linhas
                                  ),
                                  itemCount: state.length,
                                  itemBuilder:
                                      (BuildContext context, int index) {
                                    return EnterpriseWidget(
                                      onTap: () {
                                        enterpriseController
                                            .selectEnterprise(state[index]);
                                      },
                                      companyLogo:
                                          const Icon(Icons.business, size: 60),
                                      companyName: state[index].nameFancy,
                                      cnpj: state[index].cnpj,
                                    );
                                  },
                                )
                              : const Center(
                                  child: Text("Nenhum registro encontrado."),
                                ),
                        ),
                        const Padding(padding: EdgeInsets.only(top: 30)),
                        ClipRRect(
                          borderRadius: BorderRadius.circular(29),
                          child: ElevatedButton(
                            onPressed: () {
                              Modular.to
                                  .pushNamed('/enterprise/create-enterprise');
                            },
                            style: ElevatedButton.styleFrom(
                              padding: const EdgeInsets.symmetric(
                                  vertical: 20, horizontal: 40),
                              primary: Colors.green,
                              onSurface: Colors.green,
                            ),
                            child: const Text(
                              "Criar Empresa",
                              style: TextStyle(color: Colors.white),
                            ),
                          ),
                        ),
                      ],
                    )
                  ],
                ));
          },
        ));
  }
}
