DESCRIPTION = "Linux kernel for RockPi-N10"

require recipes-kernel/linux/linux-yocto.inc

# We need mkimage for the overlays
DEPENDS += "openssl-native u-boot-mkimage-radxa-native"

SRC_URI = " \
	git://github.com/radxa/kernel.git;branch=rk3399pro-toybrick-stable; \
	file://0001-UPSTREAM-arm64-vdso-Define-vdso_-start-end-as-array.patch \
	file://0001-Makefile-disable-gcc9-s-address-of-packed-member-war.patch \
"

SRCREV = "c36a21e2be755919e8b406069206e67126b7e712"
LINUX_VERSION = "4.4.167"

# Override local version in order to use the one generated by linux build system
# And not "yocto-standard"
LINUX_VERSION_EXTENSION = ""
PR = "r1"
PV = "${LINUX_VERSION}"

# Include only supported boards for now
COMPATIBLE_MACHINE = "(rk3036|rk3066|rk3288|rk3328|rk3399|rk3308|rk3399pro)"
deltask kernel_configme

do_compile_append() {
	oe_runmake dtbs
}

do_deploy_append() {
	install -d ${DEPLOYDIR}/overlays
	install -m 644 ${WORKDIR}/linux-rockpi_n10_rk3399pro-standard-build/arch/arm64/boot/dts/rockchip/overlay/* ${DEPLOYDIR}/overlays
}
