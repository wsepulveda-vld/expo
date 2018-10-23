let
  pin = builtins.fromJSON (builtins.readFile ./nixpkgs.json);

  # DONTMERGE
  #nixpkgs = builtins.fetchTarball {
  #  url = "https://github.com/NixOS/nixpkgs-channels/archive/${pin.rev}.tar.gz";
  #  inherit (pin) sha256;
  #};
  nixpkgs = builtins.fetchTarball {
    url = "https://github.com/nicknovitski/nixpkgs/archive/android-sdk-26-licenses.tar.gz";
    sha256 = "1c52sksgla3fprb2xrw47992jx3lphcr5wqs51xnl607dimflxgm";
  };


in

{ ... } @ args:

  import nixpkgs (args // {
    config = {
      allowUnfree = true;
      android_sdk.accept_license = true;
    };
  })
