#!/bin/bash

# -- settings --
PYTHON_DIR=d:\\Python34
COMP_LEVEL=9
SFX_MODULE=
# --------------

python_subdir=${PYTHON_DIR##*\\}
cp *.dll $PYTHON_DIR
cp vcredist_x86.exe ..

pushd ..

archive_name="doc-clone-miner-$(date +%Y-%m-%d).exe"
git ls-files > dcm_files.lst

echo "start \"\" \"%~dp0${python_subdir}\\pythonw.exe\" \"%~dp0element_miner_ui.py\" -ct \"%~dp0clone_miner\\clones.exe\" %*" >run.cmd

cat build_dist/7zSD.sfx build_dist/7z.sfr > build_dist/7z.fsfx

7z a build_dist\\${archive_name} -sfxbuild_dist\\7z.fsfx -mx=${COMP_LEVEL} -ms=on -i@dcm_files.lst run.cmd vcredist_x86.exe -xr!build_dist ${PYTHON_DIR}

popd
