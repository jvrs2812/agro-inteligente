class SingUpRequest {
  String? email;
  String? password;
  String? cpf;
  String? name;

  SingUpRequest({this.email, this.password, this.cpf, this.name});

  SingUpRequest.fromJson(Map<String, dynamic> json) {
    email = json['email'];
    password = json['password'];
    cpf = json['cpf'];
    name = json['name'];
  }
  SingUpRequest copyWith(
      {String? email, String? password, String? cpf, String? name}) {
    return SingUpRequest(
        email: email ?? this.email,
        password: password ?? this.password,
        cpf: cpf ?? this.cpf,
        name: name ?? this.name);
  }

  Map<String, dynamic> toJson() =>
      {'email': email, 'password': password, 'cpf': cpf, 'name': name};
}
