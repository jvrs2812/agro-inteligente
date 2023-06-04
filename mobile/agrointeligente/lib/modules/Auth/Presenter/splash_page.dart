import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../commom/constants/colors_constants.dart';

class SplashPage extends StatefulWidget {
  const SplashPage({Key? key}) : super(key: key);

  @override
  _SplashPageState createState() => _SplashPageState();
}

class _SplashPageState extends State<SplashPage> {
  @override
  void initState() {
    super.initState();
    SystemChrome.setSystemUIOverlayStyle(SystemUiOverlayStyle.light);
    Future.delayed(const Duration(milliseconds: 800))
        .whenComplete(() => Modular.to.pushNamed('/login/'));
  }

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
      backgroundColor: colorBackGround,
      body: Center(child: Text("Logo ficaria aqui")),
    );
  }
}
