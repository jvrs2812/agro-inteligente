import 'package:agrointeligente/app/app_module.dart';
import 'package:flutter/material.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';
import 'package:flutter_modular/flutter_modular.dart';

import 'app/app_widget.dart';
import 'app/auth/store/auth_store.dart';
import 'app/auth/store/enterprise_store.dart';

void main() async {
  await dotenv.load();
  runApp(ModularApp(module: MainModule(), child: const AppWidget()));
}

class MainModule extends Module {
  @override
  void exportedBinds(Injector i) {
    i.addSingleton(AuthStore.new);
    i.addSingleton(EnterpriseStore.new);
  }

  @override
  void routes(r) {
    r.module(Modular.initialRoute, module: AppModule());
  }
}
