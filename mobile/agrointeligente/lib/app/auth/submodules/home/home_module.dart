import 'package:agrointeligente/app/auth/submodules/home/domain/external/send_image_external.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/get_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/i_get_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/i_send_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/domain/repository/send_images_repository.dart';
import 'package:agrointeligente/app/auth/submodules/home/infra/i_get_image_external.dart';
import 'package:agrointeligente/app/auth/submodules/home/infra/i_send_image_external.dart';
import 'package:agrointeligente/app/auth/submodules/home/presenter/home_controler.dart';
import 'package:agrointeligente/app/auth/submodules/home/presenter/home_page.dart';
import 'package:agrointeligente/app/auth/submodules/home/presenter/image_show.dart';
import 'package:flutter_modular/flutter_modular.dart';

import '../../../../main.dart';
import '../../../shared/guards/app_guard.dart';
import 'domain/external/get_images_external.dart';

class HomeModule extends Module {
  @override
  List<Module> get imports => [MainModule()];

  @override
  void binds(i) {
    i.add<IGetImageExternal>(GetImageExternal.new);
    i.add<IGetImagesRepository>(GetImagesRepository.new);
    i.add<ISendImageExternal>(SendImageExternal.new);
    i.add<ISendImagesRepository>(SendImagesRepository.new);
    i.addSingleton(HomeController.new);
  }

  @override
  void routes(r) {
    r.child('/', child: (context) => const HomePage(), guards: [AppGuard()]);
    r.child(
      '/image',
      child: (context) => ImageShow(url: r.args.queryParams['url']!),
    );
  }
}
