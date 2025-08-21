# ---- Chapter pipeline ----
# Usage:
#   make chapter P=1 N=3 NAME=dto-models
# Produces:
#   fullstack-book/docs/part1/p1-ch3-dto-models.md
#   fullstack-book/docs/img/part1/<extracted-images>
#
# DOCX input is inferred:
#   manuscript/p1-ch3-dto-models.docx

# ---- Inputs (you pass these) ----
P     ?=
N     ?=
NAME  ?=

# Guard: NAME is required (slug after chapter number)
ifeq ($(strip $(NAME)),)
$(error NAME is required, e.g. make chapter P=1 N=3 NAME=dto-models)
endif
ifeq ($(strip $(P)),)
$(error P (part number) is required, e.g. P=1)
endif
ifeq ($(strip $(N)),)
$(error N (chapter number) is required, e.g. N=3)
endif

# ---- Derived variables (auto) ----
PART   := part$(P)
SLUG   := p$(P)-ch$(N)-$(NAME)
IN     ?= manuscript/$(SLUG).docx

OUT_MD := fullstack-book/docs/$(PART)/$(SLUG).md
OUT_IMG := fullstack-book/docs/img/$(PART)

# macOS/BSD sed uses -i '', GNU uses -i
SED_INPLACE := sed -i ''
UNAME_S := $(shell uname -s)
ifeq ($(UNAME_S),Linux)
  SED_INPLACE := sed -i
endif

.PHONY: chapter convert fix lint preview clean-md help

chapter: convert fix lint

convert:
	@mkdir -p fullstack-book/docs/$(PART) $(OUT_IMG)
	@pandoc "$(IN)" \
	  --from=docx \
	  --to=gfm \
	  --extract-media="$(OUT_IMG)" \
	  --wrap=preserve \
	  --markdown-headings=atx \
	  --reference-links \
	  --lua-filter=tools/pandoc/fix-codeblocks.lua \
	  -o "$(OUT_MD)"
	@echo "✔ Converted -> $(OUT_MD)"
	@echo "   Images   -> $(OUT_IMG)/"

fix:
	# normalize curly quotes, tabs -> 4 spaces, trim trailing spaces, remove RTL span artifacts
	@$(SED_INPLACE) \
	  -e 's/[“”]/"/g' \
	  -e "s/[‘’]/'/g" \
	  -e 's#<span dir="rtl">"</span>#"#g' \
	  -e "s#<span dir=\"rtl\">'</span>#'#g" \
	  -e 's/\t/    /g' "$(OUT_MD)" || true
	@perl -i -pe 's/[ \t]+$$//;' "$(OUT_MD)" || true
	@echo "✔ Normalized whitespace/quotes"

lint:
	# Optional markdown lint (requires: npm i -D markdownlint-cli2)
	@[ -f tools/.markdownlint.jsonc ] && npx markdownlint-cli2 "$(OUT_MD)" || true

preview:
	@cd fullstack-book && npm run start

clean-md:
	@rm -f "$(OUT_MD)"

help:
	@echo "Usage:"
	@echo "  make chapter P=<part> N=<chapter> NAME=<slug>"
	@echo "Example:"
	@echo "  make chapter P=1 N=3 NAME=dto-models"
	@echo "  (expects DOCX at manuscript/p1-ch3-dto-models.docx)"
