import os
import json

# Path configuration
INPUT_DIR = "./src/generated/resources/data/bakeries/recipe"
OUTPUT_DIR = "./src/main/resources/data/bakeries/recipe/compat/create"

# Create mod preconditions (NeoForge standard)
CREATE_CONDITION = {
    "type": "neoforge:mod_loaded",
    "modid": "create"
}

def convert_to_create_mixing(data):
    """Conversion: bakeries:blender -> create:mixing"""
    return {
        "neoforge:conditions": [CREATE_CONDITION],
        "type": "create:mixing",
        # Blender uses an ingredients list; copy it directly
        "ingredients": data.get("ingredients", []),
        # Blender uses 'output' (object); Create uses 'results' (list)
        "results": [data.get("output", {})]
    }

def convert_to_create_cutting(data):
    """Conversion: bakeries:dough_crafting_table -> create:cutting"""
    return {
        "neoforge:conditions": [CREATE_CONDITION],
        "type": "create:cutting",
        # Dough Crafting uses 'ingredient' (object); Create uses 'ingredients' (list)
        "ingredients": [data.get("ingredient", {})],
        "processing_time": 50, # Create's default processing time for cutting
        # Dough Crafting uses 'result' (object); Create uses 'results' (list)
        "results": [data.get("result", {})]
    }

def main():
    if not os.path.exists(INPUT_DIR):
        print(f"Input directory not found: {INPUT_DIR}")
        return

    # Ensure output directory exists
    os.makedirs(OUTPUT_DIR, exist_ok=True)

    count = 0

    # Traverse all JSON files in the recipe directory
    for root, dirs, files in os.walk(INPUT_DIR):
        # Skip the generated compat directory to avoid recursion loops
        if "compat" in root.replace("\\", "/"):
            continue

        for file in files:
            if not file.endswith(".json"):
                continue

            file_path = os.path.join(root, file)

            with open(file_path, "r", encoding="utf-8") as f:
                try:
                    data = json.load(f)
                except json.JSONDecodeError:
                    print(f"JSON decode error, skipping: {file_path}")
                    continue

            recipe_type = data.get("type", "")
            converted_data = None

            # Translate based on recipe type
            if recipe_type == "bakeries:blender":
                converted_data = convert_to_create_mixing(data)
            elif recipe_type == "bakeries:dough_crafting_table":
                converted_data = convert_to_create_cutting(data)

            # Write to a new file if successfully translated
            if converted_data:
                # Maintain the original filename
                output_path = os.path.join(OUTPUT_DIR, file)

                # Optional: Prefixing can resolve name collisions if different recipe types share names
                # output_path = os.path.join(OUTPUT_DIR, f"create_{file}")

                with open(output_path, "w", encoding="utf-8") as out_f:
                    json.dump(converted_data, out_f, indent=2, ensure_ascii=False)
                count += 1
                print(f"Successfully converted: {file} -> compat/create/{file}")

    print(f"\nConversion complete! Generated {count} Create mod compatibility recipes.")

if __name__ == "__main__":
    main()