import 'package:agrointeligente/commom/exception/enum/agro_enum_exception.dart';

class AgroException {
  final String? msg;
  final AgroEnumException error;

  AgroException({required this.error, this.msg});
}
