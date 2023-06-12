## [2.1.0](https://github.com/RustFields/ScaFi-core/compare/2.0.0...2.1.0) (2023-06-12)


### Features

* add branch construct ([4277933](https://github.com/RustFields/ScaFi-core/commit/4277933e908d97ff07bf336644e24decf196d015))
* add foldhood construct ([cdc3409](https://github.com/RustFields/ScaFi-core/commit/cdc3409be044bef1a2c8a8fd812a4f5bdf31dc5f))
* add nbrVar construct ([f0c5407](https://github.com/RustFields/ScaFi-core/commit/f0c54073d543faa8a0afaf1740ab7ebd0c33991c))
* add sense construct ([157dc92](https://github.com/RustFields/ScaFi-core/commit/157dc9253a88d23bbd9031619dbc68316951bfde))


### General maintenance

* [skip ci] update version in build.sbt ([8472509](https://github.com/RustFields/ScaFi-core/commit/8472509054cb500c636d44daa386b15f2d97c528))


### Dependency updates

* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.32 ([bf190a6](https://github.com/RustFields/ScaFi-core/commit/bf190a6543771e65198bf6d38076ecdae81ffabc))

## [2.0.0](https://github.com/RustFields/ScaFi-core/compare/1.1.0...2.0.0) (2023-06-06)


### ⚠ BREAKING CHANGES

* remove useless files

### Bug Fixes

* fix import ([11bed64](https://github.com/RustFields/ScaFi-core/commit/11bed64083c302e4a0da37310188448d31b82204))
* remove useless files ([fc7078b](https://github.com/RustFields/ScaFi-core/commit/fc7078bd2f4726457817611b3ee436199dfa9dbf))


### General maintenance

* [skip ci] update version in build.sbt ([0222b71](https://github.com/RustFields/ScaFi-core/commit/0222b71119d1de3d37f582812919171acf96a4e3))


### Dependency updates

* **deps:** remove dependency ([6ef114e](https://github.com/RustFields/ScaFi-core/commit/6ef114ed5e04b4ca1eaa58e820144189c3f54889))

## [1.1.0](https://github.com/RustFields/ScaFi-core/compare/1.0.0...1.1.0) (2023-06-06)


### Features

* add field calculus execution ([da15f26](https://github.com/RustFields/ScaFi-core/commit/da15f2695175968f257c0aa9c6b49b6eb776e968))
* add first base-constructs implementation ([f9e12d6](https://github.com/RustFields/ScaFi-core/commit/f9e12d65039cae79a1abd547802faeb5ce4c3819))
* create mid inside lang constructs ([098def0](https://github.com/RustFields/ScaFi-core/commit/098def06f359fa820358bd2024c743cbf8016dea))
* create VMFactory ([ad4e856](https://github.com/RustFields/ScaFi-core/commit/ad4e8564fd68a96dc6e64ee21f87fb6502e13627))


### Bug Fixes

* change functions implementations ([c4aed5f](https://github.com/RustFields/ScaFi-core/commit/c4aed5ffbe34932df155ab6d0e356c67086e3d13))
* make vm a def instead of val to allow mutability ([12f6a36](https://github.com/RustFields/ScaFi-core/commit/12f6a36c9d019750657b58fea00d43356cca8bbe))
* moved AuxiliaryConstructs to test package ([818777f](https://github.com/RustFields/ScaFi-core/commit/818777f275592475126983c9806072850e0c6cde))


### Build and continuous integration

* now Semantic Release changes version in build.sbt correctly ([a4738ba](https://github.com/RustFields/ScaFi-core/commit/a4738ba5a42925cf493c407b5c68bf4cb1a084e9))
* remove unused parameter in workflow ([6de9376](https://github.com/RustFields/ScaFi-core/commit/6de9376587d38911033a18e07427f30b658960a1))


### Dependency updates

* **deps:** update dependency sbt/sbt to v1.9.0 ([9b5a3f9](https://github.com/RustFields/ScaFi-core/commit/9b5a3f9254e3375c4a20e3fc54be8d8db52a8926))
* **deps:** update dependency semantic-release-preconfigured-conventional-commits to v1.1.30 ([3b2b90c](https://github.com/RustFields/ScaFi-core/commit/3b2b90c9912d8e5982fd6d0d944de4794ec7d515))


### Refactoring

* move vm-related stuff into a separate package ([bf9ad64](https://github.com/RustFields/ScaFi-core/commit/bf9ad648d2d948bdf8caf70bea20097724a34691))
* moved AuxiliaryConstructs to test package ([efb1e74](https://github.com/RustFields/ScaFi-core/commit/efb1e740d014683a02c92f06b07ed11c1f5acd86))
* remove useless files ([8723fec](https://github.com/RustFields/ScaFi-core/commit/8723feca2f4fd55fb54dbefbea36ebc31336e270))

## 1.0.0 (2023-05-26)


### ⚠ BREAKING CHANGES

* fixed imports
* removed unused functions
* made monads an object and moved the field instance
* rename file and move the extension methods
* remove unused files

### Features

* add an extension of language constructs based on fields ([aef8c53](https://github.com/RustFields/ScaFi-core/commit/aef8c537514ab0f5448c6d199eb5fa13f46971ad))
* add auxiliary constructs ([8fa1d94](https://github.com/RustFields/ScaFi-core/commit/8fa1d944060f4bdd702349e2a1a065560748a5d6))
* add auxiliary constructs to field lang ([8e85efe](https://github.com/RustFields/ScaFi-core/commit/8e85efeaed2843ece661f694935ad81fd705f178))
* add auxiliary constructs to fields ([83daf4a](https://github.com/RustFields/ScaFi-core/commit/83daf4af0673db8855f850c87219878a3b598921))
* add class with tests for fields ([1b7b111](https://github.com/RustFields/ScaFi-core/commit/1b7b1110fe28caf4aa25d925a53bbec53396ac9e))
* add Context ([45a0d90](https://github.com/RustFields/ScaFi-core/commit/45a0d902cfc4303a09222d52e40f64e075cc5134))
* add Defaultable type class ([c5134c7](https://github.com/RustFields/ScaFi-core/commit/c5134c7a0458777d5ceec214c8fbc084420c0a23))
* add Export implementation ([5346963](https://github.com/RustFields/ScaFi-core/commit/53469631947ffb51067ff81eee4b794fdb1b2a7e))
* add Export object with two new methods to build an export ([be5e778](https://github.com/RustFields/ScaFi-core/commit/be5e7783b4d89621d73a3332aeba8b8b4e525655))
* add Export trait ([c4b40f3](https://github.com/RustFields/ScaFi-core/commit/c4b40f3dffac5ca3f130edccf683a58d62441c96))
* add ExportFactory ([0dae115](https://github.com/RustFields/ScaFi-core/commit/0dae1153d81bf457722ca342f2f434323768a43b))
* add factory method for empty paths ([8864136](https://github.com/RustFields/ScaFi-core/commit/886413667c5f9773a81de614afbc24890e3084c6))
* add Field concept ([2105cf2](https://github.com/RustFields/ScaFi-core/commit/2105cf26e6851854099bc55c9e8745fefc4c7f4c))
* add function for changing default value ([bef09bb](https://github.com/RustFields/ScaFi-core/commit/bef09bbe3424ff10b8e52a61c6f5b3e414a896bf))
* add function for changing self value of a field ([3c6f5df](https://github.com/RustFields/ScaFi-core/commit/3c6f5df47b4d517844ef157f7a90ef7d145c320e))
* add general language specification ([d1e132b](https://github.com/RustFields/ScaFi-core/commit/d1e132b5a0e322d8ada490a6da9e00c71ff20fac))
* add given conversion from String to Sensor ([900579a](https://github.com/RustFields/ScaFi-core/commit/900579a1ec963d1f424d9cd6edc5312190169ce8))
* add instance for Defaultable list ([c3df2c8](https://github.com/RustFields/ScaFi-core/commit/c3df2c8deb6e0e1edf689d1615afcc68181d7688))
* add instances for common defaultable types ([8bcc19b](https://github.com/RustFields/ScaFi-core/commit/8bcc19bc883815d60c899bc9f72ecea1faacbdd8))
* add missing methods to Slot trait ([eee8f73](https://github.com/RustFields/ScaFi-core/commit/eee8f73bd053525397e8a6202270a3e6b6131f9e))
* add Monad type class ([d3c1c39](https://github.com/RustFields/ScaFi-core/commit/d3c1c39ee9533d20106ac6461f6c971b9a9dc296))
* add monoid concept and instances ([0f6e3a1](https://github.com/RustFields/ScaFi-core/commit/0f6e3a109d6179cb07c61d023f7c816f9395319e))
* add nbr construct ([4c8fb93](https://github.com/RustFields/ScaFi-core/commit/4c8fb930e2995cf91e9bc071d2c882b0b261e1c6))
* add nbr construct to field lang ([6e2bc99](https://github.com/RustFields/ScaFi-core/commit/6e2bc996c35a9dda4e2f557f3623125b61dd1f64))
* add nbrSense method ([5c68cbf](https://github.com/RustFields/ScaFi-core/commit/5c68cbf67ed7d05c8e200f19b19fbdf08c130a4d))
* add round vm definition ([25166dd](https://github.com/RustFields/ScaFi-core/commit/25166dd4f845f86ae5b6d0f8de27c7a702e25b54))
* add RoundVM ([67eca5b](https://github.com/RustFields/ScaFi-core/commit/67eca5b20f023a3f7a2d147c5d73d6ab5a165756))
* add sense method to Context ([a0cb34c](https://github.com/RustFields/ScaFi-core/commit/a0cb34c528e913942c289f82579db8aa899e4c21))
* add Sensor ([7128584](https://github.com/RustFields/ScaFi-core/commit/7128584d6b3347738885508d28adbd06b6028742))
* add sensor definition ([c205caf](https://github.com/RustFields/ScaFi-core/commit/c205caf2be19aaeafc9194a6af4bdac634eea02b))
* add slot definition ([5c42df7](https://github.com/RustFields/ScaFi-core/commit/5c42df7d9964ceea459b82fa4567c013bf2d67ff))
* add Status ([2c389a8](https://github.com/RustFields/ScaFi-core/commit/2c389a84a92bf0e8f8cf58a036ead36fb3b75939))
* add test case for comprehensions ([8c0c46b](https://github.com/RustFields/ScaFi-core/commit/8c0c46bbb5d381542ef3fad009a4b0404c54b243))
* add tests for field manipulation ([ff14772](https://github.com/RustFields/ScaFi-core/commit/ff147729906569a3f773448f37f8ccae5405ba41))
* add trait for tests with fields ([1d7cdc2](https://github.com/RustFields/ScaFi-core/commit/1d7cdc25dc32fc56f4eab867f29177ce4d6d23cd))
* add utility extension for function application ([351071b](https://github.com/RustFields/ScaFi-core/commit/351071b81e5d8c8d9c8ccdc8ea1d897eed12a662))
* add VM trait ([47e003f](https://github.com/RustFields/ScaFi-core/commit/47e003fbe379ca940bdae154212083b46c2b2f05))
* change Path toString formatting ([3f9ce42](https://github.com/RustFields/ScaFi-core/commit/3f9ce423d63a1adf4d4afa164301006d56d7f3b7))
* change return type of put method in Export ([c4209aa](https://github.com/RustFields/ScaFi-core/commit/c4209aaad5ec52938efa2e618c72f67bb1e9dd14))
* complete roundVM api definition ([679921d](https://github.com/RustFields/ScaFi-core/commit/679921db6ffb94a4b306fddf524e5776025de34d))
* delete ExportFactory ([0b7ee56](https://github.com/RustFields/ScaFi-core/commit/0b7ee56e31d9b34c7e27caccaf62d07685cdd838))
* extend MonadicFields with field composition ([6b72b70](https://github.com/RustFields/ScaFi-core/commit/6b72b7044bee30d1da95d7822be6924a585eaf6c))
* implement Path ([4ce5af0](https://github.com/RustFields/ScaFi-core/commit/4ce5af05997c974206425a89654d2cb1bb0a5953))
* implement VMStatus ([c093d74](https://github.com/RustFields/ScaFi-core/commit/c093d7435dc95d77185db05599d675a0e6a33efa))
* remove isEqualTo method from Path trait ([8a5951c](https://github.com/RustFields/ScaFi-core/commit/8a5951c53e689a251b8bfde7b6b0417be3bf18f7))
* rename Context methods ([138b564](https://github.com/RustFields/ScaFi-core/commit/138b564c17e83ab453ea171a0dd1e29679b254fc))


### Bug Fixes

* add dependency in trait ([2a0beb7](https://github.com/RustFields/ScaFi-core/commit/2a0beb7f8dc32f73328709507e7475d0de2495a8))
* add import ([77a01c7](https://github.com/RustFields/ScaFi-core/commit/77a01c7e891e39d15930189a960d4cd3efea4ee0))
* add target name to inline method ([f744fd0](https://github.com/RustFields/ScaFi-core/commit/f744fd0c61769313daada8d0c9c93e1af828b933))
* fix a test ([702b84a](https://github.com/RustFields/ScaFi-core/commit/702b84af3eac63c4097e8fef80a141b1c06d4cc0))
* fix import ([56287bc](https://github.com/RustFields/ScaFi-core/commit/56287bcc0425a6be7701214839e85c85dbd15ec2))
* fix import to monoid ([cd4fb53](https://github.com/RustFields/ScaFi-core/commit/cd4fb534f8408f32b0c3779aa471a7d1652ad1e5))
* fixed imports ([d025416](https://github.com/RustFields/ScaFi-core/commit/d02541613e45c21c401d5dcb34fd912c3c6c5872))
* remove a broken import ([c7b7ea1](https://github.com/RustFields/ScaFi-core/commit/c7b7ea18a4e81f818f1fc4a1ea1e8b08a29d652d))
* remove new call ([7ba278b](https://github.com/RustFields/ScaFi-core/commit/7ba278bc6312452c734d79fec67875c1121f6d3e))


### Documentation

* add docs for Field ([b4095e3](https://github.com/RustFields/ScaFi-core/commit/b4095e385d801fd0c4d3505b188f35da03d0293e))
* add documentation to Path class ([d260ad9](https://github.com/RustFields/ScaFi-core/commit/d260ad9d6739f08b2220735d48d9f02aed0d116e))


### Tests

* add a test to check Path printing ([e6c0d9c](https://github.com/RustFields/ScaFi-core/commit/e6c0d9c97ee75dc2ce9c480935b2fde57c28f021))
* add comparison tests for Exports ([d60ddb7](https://github.com/RustFields/ScaFi-core/commit/d60ddb72b9d75230d417ef4ac3738e1e8cffb2a6))
* add Context tests ([2674ce4](https://github.com/RustFields/ScaFi-core/commit/2674ce41c619740033505e7ea1d55b497c2a30cf))
* add export creation tests ([830b9a7](https://github.com/RustFields/ScaFi-core/commit/830b9a744fefe6161e5ab61952c4c8258f2f47e6))
* add ExportFactory tests ([a64671b](https://github.com/RustFields/ScaFi-core/commit/a64671b4b7f3ad8a364564998ac95d3be851c819))
* add first tests for the Export class ([659c2f3](https://github.com/RustFields/ScaFi-core/commit/659c2f33ce10c296e02aa85b3c3eaa4b38a6eb45))
* add nbrSense test ([a29950f](https://github.com/RustFields/ScaFi-core/commit/a29950fc6f530557756cf494badb3656d81a5017))
* add Path tests ([62f63a1](https://github.com/RustFields/ScaFi-core/commit/62f63a1f0b6e32f32d9b5ed70d05f05bb1fa8aad))
* add paths and getMap tests ([d4a5837](https://github.com/RustFields/ScaFi-core/commit/d4a58379ed10fc05c8f1f74d101b0acb5a79ea91))
* add root method testing ([6082c3c](https://github.com/RustFields/ScaFi-core/commit/6082c3ce7c5689ffaec46a39a0090280bcc5a873))
* add sense method test ([32d3b82](https://github.com/RustFields/ScaFi-core/commit/32d3b82a5537ff5514cbbde6dfeff1574ac12691))
* add Slot tests ([723f884](https://github.com/RustFields/ScaFi-core/commit/723f884bf4b2970c41fdfdef4d124c2d94e15b07))
* add test case for field composition ([b29847a](https://github.com/RustFields/ScaFi-core/commit/b29847a2e58e9a1d07ef9564873f55fd3c871239))
* added test case for field of function and values composition ([0d06664](https://github.com/RustFields/ScaFi-core/commit/0d0666479626ccf8cbd226d61ef37395acdd3149))
* fix test to get the value from an Export ([f4d1dd6](https://github.com/RustFields/ScaFi-core/commit/f4d1dd606024b580cba9603ecde88066e19766fa))
* renamed test package ([4d24d85](https://github.com/RustFields/ScaFi-core/commit/4d24d85cd1a9862b6be2935239d14c815fa09b9d))


### Refactoring

* add assertion for completeness ([16fac09](https://github.com/RustFields/ScaFi-core/commit/16fac09c4472acc17754a06ecb3e0e85aa3bb39b))
* add new generic function and refactor api ([06f6ddf](https://github.com/RustFields/ScaFi-core/commit/06f6ddfb60d82d676bb0a1835c4615fd89e8632e))
* add type parameter description ([46564a8](https://github.com/RustFields/ScaFi-core/commit/46564a888fbb86236e3f4e35c6546cb87033ce54))
* apply scalafmt rules ([1985225](https://github.com/RustFields/ScaFi-core/commit/19852254bd681048b56af8c86185869b2e326efa))
* change givens with summon ([7587cb3](https://github.com/RustFields/ScaFi-core/commit/7587cb318650710e546bc51a6e095b4e1a93f7d4))
* changed signature to be symmetric to changeSelf ([feed6ae](https://github.com/RustFields/ScaFi-core/commit/feed6ae24ad9e321749406216afbd8d81e432140))
* made monads an object and moved the field instance ([c62f8ec](https://github.com/RustFields/ScaFi-core/commit/c62f8ecc74b872a25044098deecaa70fc5228a2f))
* made monoid instances for cats.Monoid ([5e3122a](https://github.com/RustFields/ScaFi-core/commit/5e3122af24e81eadbffe5b9cffa7aa8436ab6bad))
* moved defaultable to functional package ([b6f1fc5](https://github.com/RustFields/ScaFi-core/commit/b6f1fc5d7772752d693081f5cf8a08889da9ee8f))
* moved monad generic definition in another package ([a0af124](https://github.com/RustFields/ScaFi-core/commit/a0af1243b21967bdae32670fb18d9d8f8fd13522))
* refactor Field to be a case class ([97a7ea6](https://github.com/RustFields/ScaFi-core/commit/97a7ea655fa5b9f59c2b52ce79ab739d8b6da3d5))
* remove unused files ([cc05394](https://github.com/RustFields/ScaFi-core/commit/cc053947eb3100a1553c2c2f3aa7cf8edc7b9c58))
* removed unused functions ([e6dd69a](https://github.com/RustFields/ScaFi-core/commit/e6dd69a7911c871a936cb8dcbf499e42e0875e20))
* rename file and move the extension methods ([428e415](https://github.com/RustFields/ScaFi-core/commit/428e4154b8dccfd1255ea2465dab6425366aa70d))
* rename file as topmost trait ([3150411](https://github.com/RustFields/ScaFi-core/commit/3150411bbe0eaa47b907f8df6864e14f9b45e945))
* rename file to match class ([720bb0c](https://github.com/RustFields/ScaFi-core/commit/720bb0cafde5d05714ecfc200fb7df6720854c69))
* use traits to manage dependancies ([dcd7bfc](https://github.com/RustFields/ScaFi-core/commit/dcd7bfc90df51ac99c0782faf20ab91d82591cc8))


### General maintenance

* add git-hooks submodule ([0b50bc5](https://github.com/RustFields/ScaFi-core/commit/0b50bc5bdad4f11e006151c0669cc9f37ad021ac))
* add members and year in LICENSE ([6af7534](https://github.com/RustFields/ScaFi-core/commit/6af7534eda8be89e0ec81f35fec2332c525f17cd))
* add scalafmt ([eba1545](https://github.com/RustFields/ScaFi-core/commit/eba15452223a1a6a5282c029b1b889773b0faa8f))
* change VM name to RoundVM ([591e4ca](https://github.com/RustFields/ScaFi-core/commit/591e4cacc3e46841abb90a50e3b2bd286a051898))
* delete default classes ([b2bf69a](https://github.com/RustFields/ScaFi-core/commit/b2bf69a295a7abb92297f9beabe7330ce11722a8))
* delete RoundVM ([7976791](https://github.com/RustFields/ScaFi-core/commit/79767910444091ddbc10ff81d1b730f2b0084749))
* delete useless files ([4fc47ad](https://github.com/RustFields/ScaFi-core/commit/4fc47ad8c00c9213bc7b2fd28e524ed198695864))
* fix import after package change ([394c6c4](https://github.com/RustFields/ScaFi-core/commit/394c6c4b5594ae2430e54e5e4ee0af74b8a25b79))
* remove comments from VMStatus class ([f1d3bee](https://github.com/RustFields/ScaFi-core/commit/f1d3bee4b640ad4d72e0954db6d804e36a2b19ea))
* remove VM package ([eb32df5](https://github.com/RustFields/ScaFi-core/commit/eb32df521cc624c5d43df40d74cf8549b78a0403))
* text formatting ([18d1940](https://github.com/RustFields/ScaFi-core/commit/18d194056f7b3313719b6d67ab43ca645a804292))


### Dependency updates

* **deps:** add renovate.json ([34fc073](https://github.com/RustFields/ScaFi-core/commit/34fc0730fc2943172dbbeea1ebc5321c51bbeadc))
* **deps:** remove unused dependency ([86244d1](https://github.com/RustFields/ScaFi-core/commit/86244d1947efe3e2675d25ba7460364e7e522d9f))
* **deps:** update dependency org.scalameta:sbt-scalafmt to v2.5.0 ([0bc65fd](https://github.com/RustFields/ScaFi-core/commit/0bc65fd7d49882b7747389c6d645f79cb4cc9a55))
* **deps:** update dependency org.scalatest:scalatest to v3.2.16 ([81918f5](https://github.com/RustFields/ScaFi-core/commit/81918f55a37bb13d4e7fd15aaf67b960fdce4efc))
* **deps:** update dependency sbt/sbt to v1.8.3 ([a10e013](https://github.com/RustFields/ScaFi-core/commit/a10e0133970c8a4921e2770e0906a48fccfb1ecd))
* **deps:** update dependency scala to v3.3.0 ([aa5580c](https://github.com/RustFields/ScaFi-core/commit/aa5580c1f428ab1d6060668abaa5cf60ea92aa13))


### Build and continuous integration

* add dependency to cats ([8b582a9](https://github.com/RustFields/ScaFi-core/commit/8b582a9c3cf6e2b57dcdc220d93b6ae90eab256f))
* add github workflows for test and release ([d0e757f](https://github.com/RustFields/ScaFi-core/commit/d0e757f29481b170d91ed40865135daff1b144d8))
* add SBT wrapper ([e3a7f9f](https://github.com/RustFields/ScaFi-core/commit/e3a7f9f5da2bbe64faf2d199998b8e064f5b6a1e))
* add Scala project ([4c32de2](https://github.com/RustFields/ScaFi-core/commit/4c32de21c672c5ea66528dd2f93b16843471e3e2))
* configure Semantic Release ([2782541](https://github.com/RustFields/ScaFi-core/commit/27825410340ba2d3ff31963957a6e41bba55bef5))
* remove sbtx ([d39753d](https://github.com/RustFields/ScaFi-core/commit/d39753d599a0d86aaf5e06b874d2be13135283c2))
* remove unused job in github workflow ([9bc31c0](https://github.com/RustFields/ScaFi-core/commit/9bc31c07a8d507c933348bac34f16371b737c81a))
