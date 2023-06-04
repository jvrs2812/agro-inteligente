import 'dart:convert';

import 'package:agrointeligente/commom/constants/url_base.dart';
import 'package:agrointeligente/commom/exception/agro_exception.dart';
import 'package:agrointeligente/commom/exception/enum/agro_enum_exception.dart';
import 'package:agrointeligente/external/iadpter_commom.dart';
import 'package:dio/dio.dart';
import 'package:either_dart/src/either.dart';

class AdpterCommom implements IAdpterCommom {
  final Dio dio = Dio();

  List<int> listStatusCodeSuccess = [200, 201];

  @override
  Future<Either<AgroException, dynamic>> inserir(
      {required String sufixoUrl,
      String? token,
      Map<dynamic, String>? body}) async {
    Options opt = token != null ? getOptions(token) : Options();

    Response response = await dio.post(UrlBase.getUrlBase() + sufixoUrl,
        options: opt, data: body);

    return deserialize(response);
  }

  Options getOptions(String token) {
    return Options(headers: {'Authorization': 'Bearer $token'});
  }

  Either<AgroException, dynamic> deserialize(Response response) {
    if (response.statusCode == 400) {
      return Left(AgroException(
          error: AgroEnumException.BadRequest,
          msg: getErros(response.data)![0]));
    } else if (response.statusCode == 404) {
      return Left(AgroException(
          error: AgroEnumException.NotFound, msg: getErros(response.data)![0]));
    }

    if (listStatusCodeSuccess.contains(response.statusCode)) {
      return Right(response.data);
    }

    return Left(AgroException(
        error: AgroEnumException.Unexpected, msg: "Erro inesperado"));
  }

  List<String>? getErros(dynamic body) {
    return (body['error'] as List).map((item) => item as String).toList();
  }
}
