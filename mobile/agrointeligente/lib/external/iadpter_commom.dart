import 'package:agrointeligente/commom/exception/agro_exception.dart';
import 'package:agrointeligente/commom/exception/enum/agro_enum_exception.dart';
import 'package:either_dart/either.dart';

abstract class IAdpterCommom {
  Either<AgroException, dynamic> inserir(
      {required String sufixoUrl, String? token, Map<dynamic, String>? body});
}
