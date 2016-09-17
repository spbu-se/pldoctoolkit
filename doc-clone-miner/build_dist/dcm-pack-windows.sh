#!/bin/bash

# -- settings --
PYTHON_DIR=d:\\Python3
COMP_LEVEL=9
SFX_MODULE=
# --------------

pushd ..

archive_name="doc-clone-miner-$(date +%Y-%m-%d).exe"
git ls-files > dcm_files.lst

echo 'start "" "%~dp0\\Python3\\pythonw.exe %~dp0\\element_miner_ui.py" -ct "%~dp0\\clone_miner\\clones.exe" %*' >run.cmd

7z a build_dist\\${archive_name} -sfxbuild_dist\\7z.sfx -mx=${COMP_LEVEL} -ms=on -i@dcm_files.lst run.cmd -xr!build_dist ${PYTHON_DIR}

popd
