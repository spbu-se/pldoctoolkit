pushd $(dirname $0)

pyrcom="/cygdrive/d/Python3.x86/python.exe D:/VCSWF/docs.git/myprogs/python/CloneVisualizer/clones2html.py"

pppa=(3)

for t in ${pppa[*]}
do

  echo y | ./clones.exe $t 0 0 >/dev/null

  if [ -f Output/$t.blacklist ]; then
    bl=-bl Output/$t.blacklist
  fi
  if [ $t = "1" ]; then
    head=yes
  else
    head=no
  fi

  cline="nice -n 20 $pyrcom -nb 100 -mv 2000 -sd $t $bl -minl 5 -cmup yes -fint no -wv yes -ph $head"
  echo $cline
  $cline

done

popd
