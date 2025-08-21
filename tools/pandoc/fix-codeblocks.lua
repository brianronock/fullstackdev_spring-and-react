function CodeBlock(cb)
  -- If no explicit class/language is provided, try to infer
  if not cb.classes or #cb.classes == 0 then
    local s = cb.text or ""

    if s:match("^%s*package%s") or s:match("class%s+%u") or s:match("@RestController") then
      cb.classes = {"java"}
    elseif s:match("^%s*(GET|POST|PUT|DELETE|PATCH)%s+/%S+") then
      cb.classes = {"http"}
    elseif s:match('"%w+"%s*:') or s:match("^%s*{") then
      cb.classes = {"json"}
    elseif s:match("^%s*(SELECT|INSERT|UPDATE|DELETE)%s+") then
      cb.classes = {"sql"}
    elseif s:match("^%s*(docker|git|npm|curl|mvn)%s") or s:match("^%s*#%s!/bin/") then
      cb.classes = {"bash"}
    elseif s:match("^%s*version:%s%d") or s:match("services:%s") then
      cb.classes = {"yaml"}
    elseif s:match("^%s*FROM%s+[%w]+") or s:match("^%s*CMD%s") then
      cb.classes = {"docker"}
    else
      -- fallback: assume JavaScript
      cb.classes = {"javascript"}
    end
  end
  return cb
end

-- If a BlockQuote begins with ```lang and ends with ```, unwrap it as a real fenced code block
function BlockQuote(el)
  local text = pandoc.utils.stringify(el)
  local lang = text:match("^%s*```([%w_+-]+)")
  local body = text:match("^%s*```[%w_+-]+%s*\n(.*)\n%s*```%s*$")
  if lang and body then
    -- Strip any leading '> ' that might remain on lines
    body = body:gsub("\n> %s?", "\n")
    return pandoc.CodeBlock(body, pandoc.Attr("", {lang}))
  end
  return el
end
