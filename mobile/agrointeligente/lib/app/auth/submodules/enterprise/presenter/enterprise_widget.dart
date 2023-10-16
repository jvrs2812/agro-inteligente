import 'package:flutter/material.dart';

class EnterpriseWidget extends StatelessWidget {
  final String companyName;
  final Icon companyLogo;
  final String cnpj;
  final VoidCallback onTap;

  const EnterpriseWidget(
      {required this.companyName,
      required this.companyLogo,
      super.key,
      required this.cnpj,
      required this.onTap});

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: onTap,
      child: Container(
        margin: const EdgeInsets.only(top: 30),
        width: 150,
        height: 150,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20.0),
        ),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Container(
              height: 100,
              width: 100,
              decoration: const BoxDecoration(
                color: Colors.greenAccent,
                shape: BoxShape.circle,
              ),
              child: companyLogo,
            ),
            const SizedBox(height: 8.0),
            Text(
              companyName,
              style: const TextStyle(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                  fontSize: 20),
            ),
            Text(
              cnpj,
              style: const TextStyle(
                  color: Colors.black,
                  fontWeight: FontWeight.bold,
                  fontSize: 10),
            ),
          ],
        ),
      ),
    );
  }
}
