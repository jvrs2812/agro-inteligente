import 'package:flutter/widgets.dart';
import 'package:flutter_modular/flutter_modular.dart';

import 'home_controler.dart';

class ImageShow extends StatelessWidget {
  const ImageShow({required this.url, super.key});

  final String url;

  @override
  Widget build(BuildContext context) {
    HomeController homeController = Modular.get();
    return SizedBox(
      height: MediaQuery.of(context).size.height / 1.2,
      width: double.infinity,
      child: Image.memory(
          homeController.getImageWithUrl(url, homeController.state)),
    );
  }
}
