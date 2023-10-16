import 'dart:async';
import 'dart:typed_data';

import 'package:agrointeligente/app/auth/store/auth_store.dart';
import 'package:agrointeligente/app/auth/store/enterprise_store.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/entities/analyze_response.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/i_get_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/i_send_images_repository.dart';
import 'package:flutter/material.dart';
import 'package:flutter_modular/flutter_modular.dart';
import 'package:flutter_triple/flutter_triple.dart';
import 'package:asuka/asuka.dart' as asuka;
import 'package:image_picker/image_picker.dart';

class HomeController extends Store<List<AnalyzeResponse>> {
  HomeController(this._images, this._imageSend) : super([]) {
    getImages();
  }

  final IGetImagesRepository _images;
  final ISendImagesRepository _imageSend;
  final EnterpriseStore enterpriseStore = Modular.get();
  final AuthStore auth = Modular.get();

  Uint8List getImageWithUrl(String url, List<AnalyzeResponse> lista) {
    for (var element in lista) {
      if (element.url == url) {
        return element.base64!;
      }
    }
    return Uint8List(0);
  }

  Future<void> takePicture() async {
    final pickedFile = await ImagePicker().pickImage(
        source: ImageSource.camera,
        imageQuality: 50,
        maxHeight: 1200,
        maxWidth: 1200);

    if (pickedFile != null) {
      asuka.showSnackBar(const SnackBar(
        content: Text('Assim que processado a lista será atualizada',
            textAlign: TextAlign.center),
        backgroundColor: Colors.green,
        duration: Duration(seconds: 3),
      ));

      var result = await _imageSend.sendImages(
          enterpriseStore.state.id, pickedFile.path);

      result.fold(
        onSuccess: (sucess) {
          getImages();
          asuka.showSnackBar(const SnackBar(
            content: Text('Recarregando Lista de imagens',
                textAlign: TextAlign.center),
            backgroundColor: Colors.green,
            duration: Duration(seconds: 1),
          ));
        },
        onFailure: (failure) {
          setLoading(false);
          return asuka.showSnackBar(SnackBar(
            content: Text('${failure.message}', textAlign: TextAlign.center),
            backgroundColor: Colors.red,
            duration: const Duration(seconds: 1),
          ));
        },
      );
    }
  }

  Future<void> getImages() async {
    EnterpriseStore enterpriseStore = Modular.get();

    setLoading(true);

    state.clear();

    var lista = await _images.getImages(enterpriseStore.state.id);

    lista.fold(
      onSuccess: (sucess) {
        update(sucess, force: true);
        setLoading(false);
      },
      onFailure: (failure) {
        update(state, force: true);
        setLoading(false);
        return asuka.showSnackBar(SnackBar(
          content: Text('${failure.message}', textAlign: TextAlign.center),
          backgroundColor: Colors.red,
          duration: const Duration(seconds: 1),
        ));
      },
    );
  }
}
