ls -v | cat -n | while read n f; do mv -n "$f" "cat_$n.png"; done
