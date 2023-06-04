class Auth {
  final String email;
  final String password;

  Auth({required this.email, required this.password});

  Auth copyWith({
    String? email,
    String? password,
  }) {
    return Auth(
      email: email ?? this.email,
      password: password ?? this.password,
    );
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['email'] = email;
    data['password'] = password;
    return data;
  }
}
