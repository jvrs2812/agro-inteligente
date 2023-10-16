import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:intl/intl.dart';

class ImageTile extends StatefulWidget {
  final AnalyzeResponse image;
  final DateTime data;

  const ImageTile({super.key, required this.image, required this.data});

  @override
  State<ImageTile> createState() => _ImageTileState();
}

class _ImageTileState extends State<ImageTile> {
  @override
  Widget build(BuildContext context) {
    DateFormat formato = DateFormat.yMMMMd().add_jms();
    return SizedBox(
        child: Padding(
      padding: const EdgeInsets.all(5),
      child: ListTile(
        onTap: () {
          Modular.to.pushNamed('/home/image?url=${widget.image.url}');
        },
        leading: SizedBox(
            height: 100,
            width: 100,
            child: Image.memory(widget.image.base64!, height: 100, width: 100)),
        title: Text(formato.format(widget.data)),
      ),
    ));
  }
}
