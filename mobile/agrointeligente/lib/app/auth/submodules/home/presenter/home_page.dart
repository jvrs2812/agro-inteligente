import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/store/enterprise_store.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:agrointeligente/app/auth/submodules/home/presenter/home_controler.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'image_tile.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final EnterpriseStore enterpriseStore = Modular.get();
  final AuthStore authStore = Modular.get();
  final HomeController homeController = Modular.get();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: () async {
          homeController.takePicture();
        },
        backgroundColor: Colors.green,
        child: const Icon(Icons.camera_alt),
      ),
      appBar: AppBar(
        shadowColor: Colors.green,
        backgroundColor: Colors.green,
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
      body: Column(
        children: [
          const Padding(padding: EdgeInsets.only(top: 20)),
          Padding(
            padding: const EdgeInsets.only(left: 20),
            child: Text(
              "Olá Seja Bem vindo, ${authStore.state.name}",
              style: const TextStyle(
                  fontSize: 20,
                  fontWeight: FontWeight.bold,
                  color: Colors.black87),
            ),
          ),
          ScopedBuilder<HomeController, List<AnalyzeResponse>>(
            store: homeController,
            onState: (context, state) {
              return SizedBox(
                height: MediaQuery.of(context).size.height - 145,
                width: double.infinity,
                child: state.isNotEmpty
                    ? ListView.builder(
                        itemCount: state.length,
                        itemBuilder: (context, index) {
                          return ImageTile(
                            image: state[index],
                            data: state[index].dateAnalyze!,
                          );
                        },
                      )
                    : const Center(
                        child: Text("Nenhum registro encontrado."),
                      ),
              );
            },
            onLoading: (context) {
              return Padding(
                padding: EdgeInsets.only(
                    top: (MediaQuery.of(context).size.height / 2) - 100),
                child: const Center(
                  child: CircularProgressIndicator(
                    valueColor: AlwaysStoppedAnimation<Color>(Colors.green),
                    color: Colors.green,
                  ),
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
