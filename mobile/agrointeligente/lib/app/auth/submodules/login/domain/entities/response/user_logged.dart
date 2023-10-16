import 'package:const_date_time/const_date_time.dart';

class UserLogged {
  final String acessToken;
  final String refreshToken;
  final int expiredAt;
  final DateTime expirationTime;
  final DateTime dateGenerate;
  final String name;

  UserLogged(
      {this.acessToken = "",
      this.refreshToken = "",
      this.expiredAt = 0,
      this.expirationTime = const ConstDateTime(0),
      this.dateGenerate = const ConstDateTime(0),
      this.name = ""});

  UserLogged copyWith(
      {String? acessToken,
      String? refreshToken,
      int? expiredAt,
      DateTime? dateGenerate,
      String? name}) {
    DateTime expiredAcessToken;

    DateTime dataGeracao;
    if (dateGenerate == null) {
      dataGeracao = DateTime(DateTime.now().year - 10);
      expiredAcessToken = DateTime(DateTime.now().year - 100);
    } else {
      expiredAcessToken = dateGenerate;
      expiredAcessToken =
          expiredAcessToken.add(Duration(milliseconds: expiredAt as int));
    }

    return UserLogged(
        acessToken: acessToken ?? this.acessToken,
        refreshToken: refreshToken ?? this.refreshToken,
        expiredAt: expiredAt ?? this.expiredAt,
        expirationTime: expiredAcessToken,
        dateGenerate: dateGenerate ?? this.dateGenerate,
        name: name ?? this.name);
  }

  factory UserLogged.fromJson(Map<String, dynamic> json) {
    DateTime expiredAcessToken = DateTime.parse(json["dateGenerate"]);
    DateTime expiracao =
        expiredAcessToken.add(Duration(milliseconds: json["expiredAt"] as int));

    return UserLogged(
        acessToken: json["access_token"] ?? '',
        expiredAt: json["expiredAt"] ?? 0,
        refreshToken: json["refresh_token"],
        expirationTime: expiracao,
        dateGenerate: DateTime.parse(json["dateGenerate"]),
        name: json["name"]);
  }

  Map toJson() => {
        'access_token': acessToken,
        'expiredAt': expiredAt,
        'refresh_token': refreshToken,
        'dateGenerate': dateGenerate.toIso8601String(),
        'name': name
      };
}
