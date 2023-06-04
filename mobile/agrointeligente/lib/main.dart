import 'package:agrointeligente/app_module.dart';
import 'package:agrointeligente/app_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_dotenv/flutter_dotenv.dart';

Future main() async {
  await dotenv.load(fileName: ".env");
  return runApp(ModularApp(module: AppModule(), child: AppWidget()));
}
